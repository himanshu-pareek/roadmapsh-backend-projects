package dev.javarush.roadmapsh_backend.weather_api.external.visual_crossing;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DayForecastResponse(
    @JsonAlias("datetime") String date,
    @JsonAlias("datetimeEpoch") long dateEpoch,
    @JsonAlias("tempmax") double maxTemperature,
    @JsonAlias("tempmin") double minTemperature,
    @JsonAlias("temp") double temperature,
    @JsonAlias("feelslikemax") double feelsMaxTemperature,
    @JsonAlias("feelslikemin") double feelsMinTemperature,
    @JsonAlias("feelslike") double feelsLikeTemperature,
    double dew,
    double humididy,
    @JsonAlias("windspeed") double windSpeed,
    double pressure,
    double visibility,
    String sunrise,
    long sunriseEpoch,
    String sunset,
    long sunsetEpoch,
    String conditions,
    String description,
    String icon
) {

}
