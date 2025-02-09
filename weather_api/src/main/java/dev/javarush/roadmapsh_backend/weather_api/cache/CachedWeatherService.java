package dev.javarush.roadmapsh_backend.weather_api.cache;

import java.time.Duration;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.WeatherService;

@Service
@Primary
public class CachedWeatherService implements WeatherService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CachedWeatherService.class);
    private static final Duration CACHE_DURATION = Duration.ofSeconds(10);

    private final WeatherService weatherService;
    private final RedisTemplate<String, CachedWeatherForecast> redisTemplate;

    public CachedWeatherService(
            @Qualifier("external") WeatherService weatherService,
            RedisConnectionFactory redisConnectionFactory) {
        this.weatherService = weatherService;
        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
    }

    @Override
    public CityForecast getForecastForCity(String city) {
        log.info("Getting forecast for city: {}", city);
        CachedWeatherForecast cachedForecast = redisTemplate.opsForValue().get(city);
        if (cachedForecast == null) {
            log.info("Cache miss for city: {}", city);
            try {
                CityForecast forecast = weatherService.getForecastForCity(city);
                cachedForecast = new CachedWeatherForecast(forecast, null);
            } catch (RuntimeException e) {
                cachedForecast = new CachedWeatherForecast(null, e);
            }
            log.info("Caching forecast for city: {}", city);
            redisTemplate.opsForValue().set(city, cachedForecast, CACHE_DURATION);
        } else {
            log.info("Cache hit for city: {}", city);
        }
        if (cachedForecast.exception() != null) {
            throw cachedForecast.exception();
        }
        return cachedForecast.cityForecast();
    }

}
