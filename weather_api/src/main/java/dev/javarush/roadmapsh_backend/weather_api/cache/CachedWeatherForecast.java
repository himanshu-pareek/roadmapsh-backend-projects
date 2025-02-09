package dev.javarush.roadmapsh_backend.weather_api.cache;

import java.io.Serializable;

import dev.javarush.roadmapsh_backend.weather_api.CityForecast;

public record CachedWeatherForecast(
        CityForecast cityForecast,
        RuntimeException exception) implements Serializable {
}
