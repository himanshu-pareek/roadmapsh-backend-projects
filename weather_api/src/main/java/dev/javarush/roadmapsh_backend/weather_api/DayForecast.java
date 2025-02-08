package dev.javarush.roadmapsh_backend.weather_api;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DayForecast(
    LocalDate date,
    TemperatureInfo temperatureInfo,
        TemperatureInfo feelsLikeTemperatureInfo,
                double dew,
        double humididy,
        double windSpeed,
        double pressure,
        double visibility,
        LocalDateTime sunrise,
        LocalDateTime sunset,
        String condition,
        String description,
        String icon
) {
    
}
