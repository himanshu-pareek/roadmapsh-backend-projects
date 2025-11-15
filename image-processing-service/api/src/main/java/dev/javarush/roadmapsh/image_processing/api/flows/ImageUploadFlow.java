package dev.javarush.roadmapsh.image_processing.api.flows;

import dev.javarush.roadmapsh.image_processing.core.event.EventPublisher;
import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;

public class ImageUploadFlow {
  private final StorageService storageService;
  private final ImageRepository imageRepository;
  private final EventPublisher eventPublisher;

  public ImageUploadFlow(
      StorageService storageService,
      ImageRepository imageRepository,
      EventPublisher eventPublisher
  ) {
    this.storageService = storageService;
    this.imageRepository = imageRepository;
    this.eventPublisher = eventPublisher;
  }

  public Image uploadImage(String title, InputStream inputStream, String username) {
    String objectRef = storageService.store(inputStream);
    String newId = this.imageRepository.getNewId();
    LocalDateTime now = LocalDateTime.now();
    Image image = new Image(
        newId,
        title,
        objectRef,
        now,
        username,
        now,
        username,
        username
    );
    image = this.imageRepository.insert(image);
    this.eventPublisher.publishEvent(new ImageUploadEvent(UUID.fromString(image.getId())));
    return image;
  }
}
