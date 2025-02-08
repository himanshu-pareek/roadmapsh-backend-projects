package dev.javarush.roadmapsh_backend.weather_api.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.CityInfo;
import dev.javarush.roadmapsh_backend.weather_api.DayForecast;
import dev.javarush.roadmapsh_backend.weather_api.TemperatureInfo;

@RestController
@RequestMapping("weather")
public class WeatherController {
    @GetMapping("{city}")
    public CityForecast getCityForecast(
        @PathVariable("city") String city
    ) {
        return new CityForecast(
            new CityInfo(12.9666, 77.5872, "Bangaluru, KA, India"),
            List.of(
                new DayForecast(
                    LocalDate.now(),
                    new TemperatureInfo(
                        86.4,
                        60.6,
                        74.1,
                        83.3,
                        60.6,
                        73.5
                    ),
                    "Clear",
                    "Clear conditions throughout the day"
                )
            )
        );
    }
}
