package dev.javarush.roadmapsh_backend.weather_api;

import java.time.LocalDate;

public record DayForecast(
    LocalDate date,
    TemperatureInfo temperatureInfo,
    String condition,
    String description
) {
    
}
