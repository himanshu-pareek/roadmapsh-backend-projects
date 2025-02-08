package dev.javarush.roadmapsh_backend.weather_api.external.visual_crossing;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAlias;

import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.CityInfo;

public record CityWeatherForecastResponse(
    double latitude,
    double longitude,
    String resolvedAddress,
    String address,
    @JsonAlias("timezone") String timeZone,
    @JsonAlias("tzoffset") double timeZoneOffset,
    Collection<DayForecastResponse> days
) {

    public CityForecast toCityForecast() {
        return new CityForecast(
                new CityInfo(latitude, longitude, resolvedAddress),
                days.stream().map(DayForecastResponse::toDayForecast).toList());
    }
}
