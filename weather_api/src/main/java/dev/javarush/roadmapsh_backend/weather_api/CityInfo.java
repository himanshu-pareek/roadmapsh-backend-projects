package dev.javarush.roadmapsh_backend.weather_api;

import java.io.Serializable;

public record CityInfo(
    double latitude,
    double longitude,
    String address
) implements Serializable {

}
