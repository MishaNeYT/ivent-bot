package ru.mishaneyt.iventbot.api;

import ru.mishaneyt.iventbot.api.callback.BotCallback;
import ru.mishaneyt.iventbot.api.callback.BotCallbackService;
import ru.mishaneyt.iventbot.api.command.BotCommand;
import ru.mishaneyt.iventbot.api.command.BotCommandService;

import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import lombok.Getter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MishaNeYT
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public abstract class AbstractBot implements Bot {
    String botToken;
    TelegramClient telegramClient;
    BotCommandService commandService;
    BotCallbackService callbackService;

    public AbstractBot(final String botToken) {
        this.botToken = botToken;
        this.telegramClient = new OkHttpTelegramClient(botToken);
        this.commandService = new BotCommandService(telegramClient);
        this.callbackService = new BotCallbackService(telegramClient);

        commands().forEach(commandService::registerCommand);
        callbacks().forEach(callbackService::registerCallback);
    }

    protected abstract List<BotCommand> commands();

    protected abstract List<BotCallback> callbacks();

    @Override
    public void consume(final @NotNull Update update) {
        if (update.hasCallbackQuery()) {
            callbackService.handleCallback(update.getCallbackQuery());
            return;
        }

        final Message message = update.getMessage();
        if (update.hasMessage() && message.hasText()) {
            final String messageText = message.getText();
            for (final BotCommand command : this.commandService.commandsMap().values()) {
                if (messageText.equals(command.label())) {
                    final String[] args = messageText.split(" ");
                    command.executeCommand(message, args);
                }
            }
        }
    }
}
