package dev.javarush.roadmapsh.image_processing.core;

public record ImageMetadata(int height, int width, ImageFormat format, long sizeInBytes) {
}
