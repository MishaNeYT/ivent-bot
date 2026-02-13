package ru.mishaneyt.iventbot.api.callback;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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
public abstract class BotCallback {
    @Setter
    TelegramClient telegramClient;
    CallbackQuery callbackQuery;
    String[] args;

    public abstract String callbackId();

    public abstract void execute(final CallbackQuery callbackQuery,
                                 final String[] args);

    public void executeCallback(final CallbackQuery callbackQuery,
                                final String[] args) {
        this.callbackQuery = callbackQuery;
        this.args = args;
        this.execute(callbackQuery, args);
    }

    @SneakyThrows
    public void editMessage(final List<String> messages,
                            final @Nullable ReplyKeyboard keyboard) {
        if (this.callbackQuery == null) return;

        if (telegramClient != null) {
            final EditMessageText editMessage = EditMessageText.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .text(String.join("\n", messages))
                .parseMode(ParseMode.HTML)
                .replyMarkup((InlineKeyboardMarkup) keyboard)
                .build();

            telegramClient.execute(editMessage);
        }
    }

    @SneakyThrows
    public void editMessage(final List<String> messages) {
        editMessage(messages, null);
    }

    @SneakyThrows
    public void sendPhoto(final String photoUrl, final List<String> caption,
                          final @Nullable ReplyKeyboard keyboard) {
        if (this.callbackQuery == null) return;

        if (telegramClient != null) {
            final SendPhoto sendPhoto = SendPhoto.builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .photo(new InputFile(photoUrl))
                .caption(String.join("\n", caption))
                .parseMode(ParseMode.HTML)
                .replyMarkup(keyboard)
                .build();

            telegramClient.execute(sendPhoto);
        }
    }

    @SneakyThrows
    public void sendPhoto(final String photoUrl, final List<String> caption) {
        sendPhoto(photoUrl, caption, null);
    }

    @SneakyThrows
    public void answerCallbackQuery() {
        if (this.callbackQuery == null) return;

        if (telegramClient != null) {
            telegramClient.execute(
                AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQuery.getId())
                    .build()
            );
        }
    }

    @SneakyThrows
    public void answerCallbackQuery(final String text, final boolean showAlert) {
        if (this.callbackQuery == null) return;

        if (telegramClient != null) {
            telegramClient.execute(
                AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackQuery.getId())
                    .text(text)
                    .showAlert(showAlert)
                    .build()
            );
        }
    }
}
