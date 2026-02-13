package ru.mishaneyt.iventbot.callback;

import ru.mishaneyt.iventbot.api.callback.BotCallback;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MishaNeYT
 */
public final class BackToMenuCallback extends BotCallback {

    @Override
    public @NotNull String callbackId() {
        return "back_to_menu";
    }

    @Override
    public void execute(final CallbackQuery callbackQuery, final String[] args) {
        final InlineKeyboardMarkup keyboard = InlineKeyboardMarkup.builder()
            .keyboardRow(
                new InlineKeyboardRow(InlineKeyboardButton.builder()
                    .text("🏆 Лучшие события")
                    .callbackData("recommended")
                    .build()
                )
            )
            .keyboardRow(
                new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                        .text("🥇 Популярное")
                        .callbackData("events:8:popular")
                        .build(),
                    InlineKeyboardButton.builder()
                        .text("🏅 Скоро")
                        .callbackData("soon:8")
                        .build()
                )
            )
            .keyboardRow(
                new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                        .text("💼 Профиль")
                        .callbackData("maintenance")
                        .build(),
                    InlineKeyboardButton.builder()
                        .text("⚙️ Настройки")
                        .callbackData("maintenance")
                        .build()
                )
            )
            .keyboardRow(
                new InlineKeyboardRow(InlineKeyboardButton.builder()
                    .text("💬 Поддержка")
                    .callbackData("maintenance")
                    .build()
                )
            )
            .build();

        editMessage(List.of(
            "\uD83D\uDC4B <b>Добро пожаловать!</b>",
            "",
            "Я — бот, который помогает быть в курсе всех актуальных мероприятий в городе Якутск \uD83C\uDF89",
            "",
            "Выбирай интересующую категорию ниже и узнай, куда сходить уже сегодня!"
        ), keyboard);
        answerCallbackQuery();
    }
}
