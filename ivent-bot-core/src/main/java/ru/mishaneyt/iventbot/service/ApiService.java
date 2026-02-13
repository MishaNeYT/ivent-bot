package ru.mishaneyt.iventbot.service;

import ru.mishaneyt.iventbot.model.ApiResponse;
import ru.mishaneyt.iventbot.model.Event;

import com.google.gson.Gson;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

/**
 * @author MishaNeYT
 */
@Slf4j
public final class ApiService {
    private static final String BASE_URL = "https://api.appmost.ru/v1/afisha/events";
    private static final int CITY_ID = 125;
    private static final Gson gson = new Gson();

    public static List<Event> recommendedEvents() {
        return fetchEvents(BASE_URL + "/recommended?city_id=" + CITY_ID);
    }

    public static List<Event> popularEvents(final int count) {
        return fetchEvents(BASE_URL + "?city_id=" + CITY_ID + "&count=" + count + "&sort=popular");
    }

    public static List<Event> soonEvents(final int count) {
        return fetchEvents(BASE_URL + "/soon?city_id=" + CITY_ID + "&count=" + count);
    }

    @SneakyThrows
    public static @Nullable Event eventById(final long eventId) {
        final String urlString = BASE_URL + "/" + eventId;
        final String jsonResponse = makeRequest(urlString);
        if (jsonResponse != null) {
            return gson.fromJson(jsonResponse, Event.class);
        }
        return null;
    }

    @SneakyThrows
    private static List<Event> fetchEvents(final String urlString) {
        final String jsonResponse = makeRequest(urlString);
        if (jsonResponse != null) {
            final ApiResponse response = gson.fromJson(jsonResponse, ApiResponse.class);
            return response.data();
        }
        return Collections.emptyList();
    }

    private static @Nullable String makeRequest(final String urlString) {
        HttpURLConnection connection = null;
        try {
            final URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            final int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                final StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}
