package ru.mishaneyt.iventbot.api.command;

import org.telegram.telegrambots.meta.generics.TelegramClient;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MishaNeYT
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public final class BotCommandService {
    TelegramClient telegramClient;
    Map<String, BotCommand> commandsMap = new ConcurrentHashMap<>();

    public void registerCommand(final @NotNull BotCommand command) {
        command.telegramClient(telegramClient);

        this.commandsMap.putIfAbsent(command.label(), command);
    }

    public @NotNull @Unmodifiable Map<String, BotCommand> commandsMap() {
        return Map.copyOf(commandsMap);
    }
}
