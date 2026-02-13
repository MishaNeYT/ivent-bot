package ru.mishaneyt.iventbot.command;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import ru.mishaneyt.iventbot.api.command.BotCommand;

import org.telegram.telegrambots.meta.api.objects.message.Message;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MishaNeYT
 */
public final class StartCommand extends BotCommand {

    @Override
    public @NotNull String label() {
        return "/start";
    }

    @Override
    public void execute(final Message message, final String[] args) {
        sendMessage(
            List.of(
                "\uD83D\uDC4B <b>Добро пожаловать!</b>",
                "",
                "Я — бот, который помогает быть в курсе всех актуальных мероприятий в городе Якутск \uD83C\uDF89",
                "",
                "Выбирай интересующую категорию ниже и узнай, куда сходить уже сегодня!"
            ),
            InlineKeyboardMarkup.builder()
                .keyboardRow(
                    new InlineKeyboardRow(InlineKeyboardButton.builder()
                        .text("\uD83C\uDFC6 Лучшие события")
                        .callbackData("recommended")
                        .build()
                    )
                )
                .keyboardRow(
                    new InlineKeyboardRow(
                        InlineKeyboardButton.builder()
                            .text("\uD83E\uDD47 Популярное")
                            .callbackData("events:8:popular")
                            .build(),
                        InlineKeyboardButton.builder()
                            .text("\uD83C\uDFC5 Скоро")
                            .callbackData("soon:8")
                            .build()
                    )
                )
                .keyboardRow(
                    new InlineKeyboardRow(
                        InlineKeyboardButton.builder()
                            .text("\uD83D\uDCBC Профиль")
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
                        .text("\uD83D\uDCAC Поддержка")
                        .callbackData("maintenance")
                        .build()
                    )
                )
                .build()
        );
    }
}
