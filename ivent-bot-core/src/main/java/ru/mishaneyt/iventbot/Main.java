package ru.mishaneyt.iventbot;

import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import picocli.CommandLine;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MishaNeYT
 */
@CommandLine.Command(
    name = "iventbot",
    mixinStandardHelpOptions = true,
    version = "1.0.0"
)
@Slf4j
public final class Main implements Runnable {

    @CommandLine.Option(
        names = "--token",
        description = "Bot token",
        required = true
    )
    private String botToken;

    @SneakyThrows
    @Override
    public void run() {
        final TelegramBotsLongPollingApplication botsApplication = new TelegramBotsLongPollingApplication();
        botsApplication.registerBot(botToken, new IventBot(botToken));

        logger.info("Bot successfully started!");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                botsApplication.close();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }));

        Thread.currentThread().join();
    }

    public static void main(final String[] args) {
        final int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
