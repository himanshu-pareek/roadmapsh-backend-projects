package dev.javarush.roadmapsh.image_processing.api.flows;

import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import java.util.Collection;

public class ImageGetFlow {
  private final ImageRepository imageRepository;

  public ImageGetFlow(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public Collection<Image> getImagesForUser(String username) {
    return imageRepository.findByOwner(username);
  }
}
