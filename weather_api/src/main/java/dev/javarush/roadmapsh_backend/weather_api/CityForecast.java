package dev.javarush.roadmapsh_backend.weather_api;

import java.util.Collection;

public record CityForecast(
    CityInfo cityInfo,
    Collection<DayForecast> days
) {

}
