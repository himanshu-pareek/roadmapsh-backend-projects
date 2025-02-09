package dev.javarush.roadmapsh_backend.weather_api;

import java.io.Serializable;

public record TemperatureInfo(double max, double min, double temp) implements Serializable {
}
