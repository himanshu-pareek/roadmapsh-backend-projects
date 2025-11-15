package dev.javarush.roadmapsh.image_processing.core;

public record ImageCropTransformation(
    int width,
    int height,
    int x,
    int y
) {
}
