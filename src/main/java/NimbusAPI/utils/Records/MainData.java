package NimbusAPI.utils.Records;

public record MainData(
        double temp,
        double feels_like,
        double temp_min,
        double temp_max,
        int pressure,
        int humidity,
        int sea_level,
        int grnd_level
) {
}

 record Weather(
        int id,
        String main,
        String description,
        String icon
) {
}

 record Coord(
        double lon,
        double lat
) {
}

 record Wind(
        double speed,
        int deg,
        double gust
) {
}
 record Clouds(
        int all
) {
}

 record Sys(
        int type,
        int id,
        String country,
        long sunrise,
        long sunset
) {
}
