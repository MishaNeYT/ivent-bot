package ru.mishaneyt.iventbot.model;

import lombok.Data;

import java.util.List;

/**
 * @author MishaNeYT
 */
@Data
public class ApiResponse {
    private List<Event> data;
}
