package ru.mishaneyt.iventbot.api.callback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MishaNeYT
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public final class BotCallbackService {
    TelegramClient telegramClient;
    Map<String, BotCallback> callbacksMap = new HashMap<>();

    public BotCallbackService(final TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void registerCallback(final @NotNull BotCallback callback) {
        callback.telegramClient(telegramClient);
        callbacksMap.put(callback.callbackId(), callback);
    }

    public void handleCallback(final @NotNull CallbackQuery callbackQuery) {
        final String callbackData = callbackQuery.getData();
        if (callbackData == null || callbackData.isEmpty()) {
            return;
        }

        final String[] parts = callbackData.split(":");
        final String callbackId = parts[0];

        final String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, args.length);

        final BotCallback callback = callbacksMap.get(callbackId);
        if (callback != null) {
            callback.executeCallback(callbackQuery, args);
        }
    }
}
