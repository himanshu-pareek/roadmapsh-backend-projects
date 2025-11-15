package dev.javarush.roadmapsh.image_processing.core.repository;

import dev.javarush.roadmapsh.image_processing.core.Image;
import java.util.Collection;

public interface ImageRepository {
    Image insert(Image image);

  Collection<Image> findByOwner(String username);

  void update(Image image);

  String getNewId();

  Image findById(String imageId);
}
