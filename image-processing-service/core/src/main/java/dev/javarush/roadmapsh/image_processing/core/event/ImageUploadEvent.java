package dev.javarush.roadmapsh.image_processing.core.event;

import java.io.Serializable;
import java.util.UUID;

public record ImageUploadEvent(UUID imageId) implements Serializable {
}
