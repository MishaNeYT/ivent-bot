package ru.mishaneyt.iventbot.api;

import ru.mishaneyt.iventbot.api.command.BotCommandService;

import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author MishaNeYT
 */
public interface Bot extends LongPollingSingleThreadUpdateConsumer {

    String botToken();

    TelegramClient telegramClient();

    BotCommandService commandService();
}
