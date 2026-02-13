package ru.mishaneyt.iventbot.callback;

import ru.mishaneyt.iventbot.api.callback.BotCallback;
import ru.mishaneyt.iventbot.model.Event;
import ru.mishaneyt.iventbot.service.ApiService;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MishaNeYT
 */
public final class EventDetailCallback extends BotCallback {

    @Override
    public @NotNull String callbackId() {
        return "event_detail";
    }

    @Override
    public void execute(final CallbackQuery callbackQuery, final String @NotNull [] args) {
        if (args.length == 0) {
            answerCallbackQuery("❌ Ошибка: не указан ID мероприятия", true);
            return;
        }

        long eventId;
        try {
            eventId = Long.parseLong(args[0]);
        } catch (NumberFormatException exception) {
            answerCallbackQuery("❌ Ошибка: неверный ID мероприятия", true);
            return;
        }

        final Event event = findEventInCategories(eventId);

        if (event == null) {
            answerCallbackQuery("❌ Мероприятие не найдено", true);
            return;
        }

        final List<String> caption = buildEventCaption(event);
        final InlineKeyboardMarkup keyboard = buildEventKeyboard(event);

        if (event.poster() != null && !event.poster().isEmpty()) {
            sendPhoto(event.poster(), caption, keyboard);
        } else {
            editMessage(caption, keyboard);
        }

        answerCallbackQuery();
    }

    private @Nullable Event findEventInCategories(final long eventId) {
        List<Event> events = ApiService.recommendedEvents();

        for (final Event event : events) {
            if (event.id().equals(eventId)) {
                return event;
            }
        }

        events = ApiService.popularEvents(8);
        for (final Event event : events) {
            if (event.id().equals(eventId)) {
                return event;
            }
        }

        events = ApiService.soonEvents(8);
        for (final Event event : events) {
            if (event.id().equals(eventId)) {
                return event;
            }
        }

        return null;
    }

    private @NotNull List<String> buildEventCaption(final @NotNull Event event) {
        final List<String> lines = new ArrayList<>();

        lines.add("🎭 <b>" + event.name() + "</b>");
        lines.add("");

        if (event.afishaType() != null) {
            lines.add(">Категория: " + event.afishaType());
        }

        if (event.minPrice() != null) {
            lines.add(">Цена: от " + event.formattedPrice());
        }

        if (event.ageRating() != null) {
            lines.add(">Возраст: " + event.ageRating());
        }

        if (event.duration() != null && event.duration() > 0) {
            lines.add(">Длительность: " + event.duration() + " мин");
        }

        if (event.endDate() != null) {
            lines.add(">Дата: " + event.formattedDate());
        }

        return lines;
    }

    private InlineKeyboardMarkup buildEventKeyboard(final @NotNull Event event) {
        final InlineKeyboardMarkup.InlineKeyboardMarkupBuilder<?, ?> keyboard = InlineKeyboardMarkup.builder();

        if (event.link() != null && !event.link().isEmpty()) {
            keyboard.keyboardRow(
                new InlineKeyboardRow(
                    InlineKeyboardButton.builder()
                        .text("🎫 Купить билет")
                        .url(event.link())
                        .build()
                )
            );
        }
        keyboard.keyboardRow(
            new InlineKeyboardRow(
                InlineKeyboardButton.builder()
                    .text("◀️ Назад")
                    .callbackData("recommended")
                    .build()
            )
        );

        return keyboard.build();
    }
}
