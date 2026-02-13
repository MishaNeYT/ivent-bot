package ru.mishaneyt.iventbot.callback;

import ru.mishaneyt.iventbot.api.callback.BotCallback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import org.jetbrains.annotations.NotNull;

/**
 * @author MishaNeYT
 */
public final class BackToListCallback extends BotCallback {

    @Override
    public @NotNull String callbackId() {
        return "back_to_list";
    }

    @Override
    public void execute(final CallbackQuery callbackQuery, final String[] args) {
        final RecommendedCallback recommendedCallback = new RecommendedCallback();
        recommendedCallback.telegramClient(telegramClient());
        recommendedCallback.executeCallback(callbackQuery, new String[0]);
    }
}
