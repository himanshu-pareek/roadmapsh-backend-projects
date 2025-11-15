package dev.javarush.roadmapsh.image_processing.api.dto;

import dev.javarush.roadmapsh.image_processing.core.Image;
import java.util.Collection;

public record ImageListResponse(
    Collection<ImageResponse> data
) {
  public static ImageListResponse fromImages(Collection<Image> images) {
    return new ImageListResponse(
        images.stream().map(ImageResponse::fromImage).toList()
    );
  }
}
