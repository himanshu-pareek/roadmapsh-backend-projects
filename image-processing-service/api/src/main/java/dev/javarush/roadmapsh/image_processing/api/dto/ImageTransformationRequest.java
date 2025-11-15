package dev.javarush.roadmapsh.image_processing.api.dto;

import dev.javarush.roadmapsh.image_processing.core.ImageCropTransformation;
import dev.javarush.roadmapsh.image_processing.core.ImageFiltersTransformation;
import dev.javarush.roadmapsh.image_processing.core.ImageFormat;
import dev.javarush.roadmapsh.image_processing.core.ImageResizeTransformation;

public record ImageTransformationRequest(
    ImageResizeTransformation resize,
    ImageCropTransformation crop,
    int rotate,
    ImageFormat format,
    ImageFiltersTransformation filters
) {
}
