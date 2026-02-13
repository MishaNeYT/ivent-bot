package ru.mishaneyt.iventbot.api.command;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import lombok.*;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author MishaNeYT
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public abstract class BotCommand {
    @Setter
    TelegramClient telegramClient;
    Message message;
    String[] args;

    public abstract String label();

    public abstract void execute(final Message message, final String[] args);

    public void executeCommand(final Message message, final String[] args) {
        this.message = message;
        this.args = args;

        this.execute(message, args);
    }

    private SendMessage sendMessage0(final List<String> messages) {
        return SendMessage.builder()
            .chatId(message().getChatId())
            .text(String.join("\n", messages))
            .parseMode(ParseMode.HTML)
            .build();
    }

    @SneakyThrows
    public void sendMessage(final List<String> messages) {
        sendMessage(messages, null);
    }

    @SneakyThrows
    public void sendMessage(final List<String> messages, final @Nullable ReplyKeyboard keyboard) {
        if (this.message == null) return;

        if (telegramClient != null) {
            final SendMessage sendMessage = sendMessage0(messages);
            if (keyboard != null) {
                sendMessage.setReplyMarkup(keyboard);
            }
            telegramClient.execute(sendMessage);
        }
    }
}
