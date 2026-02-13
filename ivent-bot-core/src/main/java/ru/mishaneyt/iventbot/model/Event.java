package ru.mishaneyt.iventbot.model;

import com.google.gson.annotations.SerializedName;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

/**
 * @author MishaNeYT
 */
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {
    Long id;
    String name;
    String type;

    @SerializedName("afisha_type")
    String afishaType;

    @SerializedName("sale_type")
    String saleType;

    @SerializedName("min_price")
    Long minPrice;

    String poster;

    @SerializedName("age_rating")
    String ageRating;

    Integer duration;

    @SerializedName("end_date")
    String endDate;

    Trailer trailer;

    String link;

    @SerializedName("activity_configs")
    private String activityConfigs;

    public String formattedPrice() {
        if (minPrice == null) return "Цена не указана";
        return String.format("%,d ₽", minPrice / 100);
    }

    public String formattedDate() {
        if (endDate == null) return "";
        return endDate;
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Trailer {
        String hd;
        String thumbnail;
    }
}
