package ru.mishaneyt.iventbot.callback;

import ru.mishaneyt.iventbot.api.callback.BotCallback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import org.jetbrains.annotations.NotNull;

/**
 * @author MishaNeYT
 */
public final class MaintenanceCallback extends BotCallback {

    @Override
    public @NotNull String callbackId() {
        return "maintenance";
    }

    @Override
    public void execute(final CallbackQuery callbackQuery, final String[] args) {
        answerCallbackQuery("🚧 Эта функция находится в разработке", true);
    }
}

