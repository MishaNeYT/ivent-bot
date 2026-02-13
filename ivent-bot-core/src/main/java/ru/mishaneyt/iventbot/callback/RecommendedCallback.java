package ru.mishaneyt.iventbot.callback;

import ru.mishaneyt.iventbot.api.callback.BotCallback;
import ru.mishaneyt.iventbot.model.Event;
import ru.mishaneyt.iventbot.service.ApiService;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author MishaNeYT
 */
public final class RecommendedCallback extends BotCallback {

    @Override
    public @NotNull String callbackId() {
        return "recommended";
    }

    @Override
    public void execute(final CallbackQuery callbackQuery, final String[] args) {
        final List<Event> events = ApiService.recommendedEvents();

        if (events.isEmpty()) {
            answerCallbackQuery("❌ Не удалось загрузить мероприятия", true);
            return;
        }

        final List<String> messageLines = List.of(
            "🏆 <b>Лучшие события в Якутске</b>",
            "",
            "Выберите мероприятие, чтобы узнать подробности:"
        );

        final InlineKeyboardMarkup.InlineKeyboardMarkupBuilder<?, ?> keyboard = InlineKeyboardMarkup.builder();

        for (final Event event : events) {
            keyboard.keyboardRow(
                new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                        .text(truncateText(event.name(), 60))
                        .callbackData("event_detail:" + event.id())
                        .build()
                )
            );
        }
        keyboard.keyboardRow(
            new InlineKeyboardRow(
                InlineKeyboardButton.builder()
                    .text("◀️ Назад")
                    .callbackData("back_to_menu")
                    .build()
            )
        );

        editMessage(messageLines, keyboard.build());
        answerCallbackQuery();
    }

    private @NotNull String truncateText(final String text, final int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
