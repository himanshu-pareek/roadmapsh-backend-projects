package dev.javarush.roadmapsh_backend.weather_api;

public interface WeatherService {
    CityForecast getForecastForCity(String city);
}
