package dev.javarush.roadmapsh_backend.weather_api;

public record CityInfo(
    double latitude,
    double longitude,
    String address
) {

}
