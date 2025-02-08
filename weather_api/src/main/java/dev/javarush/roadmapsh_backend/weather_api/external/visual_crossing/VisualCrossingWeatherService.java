package dev.javarush.roadmapsh_backend.weather_api.external.visual_crossing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import dev.javarush.roadmapsh_backend.weather_api.CityForecast;
import dev.javarush.roadmapsh_backend.weather_api.WeatherService;
import dev.javarush.roadmapsh_backend.weather_api.exceptions.BadRequestException;

@Service("external")
public class VisualCrossingWeatherService implements WeatherService {

    private static final String DEFAULT_QUERY_PARAMETERS = "unitGroup=metric&include=days%2Ccurrent&key={API_KEY}&contentType=json";
    private static final Logger LOGGER = LoggerFactory.getLogger(VisualCrossingWeatherService.class);
    private static final String BASE_URI = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline";

    @Value("${visual-crossing.api-key}")
    private String apiKey;

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
                    LOGGER.error("Got 4xx error for city {}", city);
                    throw new BadRequestException("Invalid city " + city);
            })
            .toEntity(CityWeatherForecastResponse.class);
        LOGGER.info("Got response {}", response.getStatusCode());
        return response.getBody().toCityForecast();
    }

    private String buildUri(String city) {
        String uri = BASE_URI + "/" + city;
        return uri + "?" + DEFAULT_QUERY_PARAMETERS.replace("{API_KEY}", apiKey);
    }

}
