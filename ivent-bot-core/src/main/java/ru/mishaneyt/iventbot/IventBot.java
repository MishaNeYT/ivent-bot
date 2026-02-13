package ru.mishaneyt.iventbot;

import ru.mishaneyt.iventbot.api.AbstractBot;
import ru.mishaneyt.iventbot.api.callback.BotCallback;
import ru.mishaneyt.iventbot.api.command.BotCommand;
import ru.mishaneyt.iventbot.callback.*;
import ru.mishaneyt.iventbot.command.StartCommand;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

/**
 * @author MishaNeYT
 */
public final class IventBot extends AbstractBot {

    public IventBot(final String botToken) {
        super(botToken);
    }

    @Override
    protected @NotNull @Unmodifiable List<BotCommand> commands() {
        return List.of(
            new StartCommand()
        );
    }

    @Override
    protected @NotNull @Unmodifiable List<BotCallback> callbacks() {
        return List.of(
            new RecommendedCallback(),
            new EventsCallback(),
            new SoonCallback(),
            new EventDetailCallback(),
            new BackToMenuCallback(),
            new BackToListCallback(),
            new MaintenanceCallback()
        );
    }
}

