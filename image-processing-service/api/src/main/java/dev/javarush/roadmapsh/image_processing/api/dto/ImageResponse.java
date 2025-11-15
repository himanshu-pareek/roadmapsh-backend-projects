package dev.javarush.roadmapsh.image_processing.api.dto;

import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.ImageFormat;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import java.time.LocalDateTime;
import java.util.Collection;

public record ImageResponse(
    String id,
  String title,
  int height,
  int width,
  ImageFormat format,
  Collection<ImageTransformation> transformations,
  long sizeInBytes,
  LocalDateTime createdAt,
  LocalDateTime updatedAt,
  String createdBy,
  String updatedBy,
    String owner
) {
  public static ImageResponse fromImage(Image image) {
    return new ImageResponse(
        image.getId(),
        image.getTitle(),
        image.getHeight(),
        image.getWidth(),
        image.getFormat(),
        image.getTransformations(),
        image.getSizeInBytes(),
        image.getCreatedAt(),
        image.getUpdatedAt(),
        image.getCreatedBy(),
        image.getUpdatedBy(),
        image.getOwner()
    );
  }
}
