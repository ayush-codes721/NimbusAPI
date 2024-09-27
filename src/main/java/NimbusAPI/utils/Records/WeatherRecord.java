package NimbusAPI.utils.Records;

import java.util.List;

public record WeatherRecord(
        String name,          // The city name
        MainData main,        // The main weather data
        List<Weather> weather,// List of weather details
        Coord coord,          // Coordinates of the city
        Wind wind,            // Wind data
        Clouds clouds,        // Cloud data
        Sys sys,              // System data like country and sunrise/sunset
        int visibility,       // Visibility in meters
        long dt,              // Time of data calculation
        int timezone,         // Shift in seconds from UTC
        int id,               // City ID
        int cod               // Internal parameter (200 = success)
) {
}
