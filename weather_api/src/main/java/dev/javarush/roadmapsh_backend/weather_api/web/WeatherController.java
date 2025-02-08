package dev.javarush.roadmapsh_backend.weather_api.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.WeatherService;

@RestController
@RequestMapping("weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("{city}")
    public CityForecast getCityForecast(
        @PathVariable("city") String city
    ) {
        return this.weatherService.getForecastForCity(city);
    }
}
