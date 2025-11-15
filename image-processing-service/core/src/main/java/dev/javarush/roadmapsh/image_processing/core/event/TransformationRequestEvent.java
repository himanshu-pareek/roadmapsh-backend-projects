package dev.javarush.roadmapsh.image_processing.core.event;

import java.util.UUID;

public record TransformationRequestEvent(
    String imageId,
    UUID transformationId
) {
}
