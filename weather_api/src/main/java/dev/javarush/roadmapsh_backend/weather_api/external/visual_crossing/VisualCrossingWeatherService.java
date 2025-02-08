package dev.javarush.roadmapsh_backend.weather_api.external.visual_crossing;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.CityInfo;
import dev.javarush.roadmapsh_backend.weather_api.DayForecast;
import dev.javarush.roadmapsh_backend.weather_api.TemperatureInfo;
import dev.javarush.roadmapsh_backend.weather_api.WeatherService;

@Service("external")
public class VisualCrossingWeatherService implements WeatherService {

    private static final String DEFAULT_QUERY_PARAMETERS = "unitGroup=metric&include=days%2Ccurrent&key=<Your Key here>&contentType=json";
    private static final Logger LOGGER = LoggerFactory.getLogger(VisualCrossingWeatherService.class);
    private static final String BASE_URI = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";

    private final RestClient restClient;

    public VisualCrossingWeatherService(RestClient.Builder builder) {
        restClient = builder.build();
    }

    @Override
    public CityForecast getForecastForCity(String city) {
        String uri = buildUri(city);
        LOGGER.info("Sending request {}", uri);
        var response = this.restClient.get()
            .uri(uri)
            .accept(org.springframework.http.MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                throw new IllegalArgumentException(res.toString());
            })
            .toEntity(CityWeatherForecastResponse.class);
        LOGGER.info("Got response {}", response.getStatusCode());
        return new CityForecast(
            new CityInfo(12.9666, 77.5872, city + " " + city),
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

    private String buildUri(String city) {
        String uri = BASE_URI + "/" + city;
        return uri + "?" + DEFAULT_QUERY_PARAMETERS;
    }

}
