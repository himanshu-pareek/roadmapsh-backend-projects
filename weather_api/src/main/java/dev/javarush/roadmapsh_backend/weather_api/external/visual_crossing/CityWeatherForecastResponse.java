package dev.javarush.roadmapsh_backend.weather_api.external.visual_crossing;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAlias;

public record CityWeatherForecastResponse(
    double latitude,
    double longitude,
    String resolvedAddress,
    String address,
    @JsonAlias("timezone") String timeZone,
    @JsonAlias("tzoffset") double timeZoneOffset,
    Collection<DayForecastResponse> days
) {
    
}
