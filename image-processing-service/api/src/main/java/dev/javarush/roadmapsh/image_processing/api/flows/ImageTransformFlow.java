package dev.javarush.roadmapsh.image_processing.api.flows;

import dev.javarush.roadmapsh.image_processing.api.dto.ImageTransformationRequest;
import dev.javarush.roadmapsh.image_processing.core.event.EventPublisher;
import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import dev.javarush.roadmapsh.image_processing.core.event.TransformationRequestEvent;
import dev.javarush.roadmapsh.image_processing.core.TransformationStatus;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class ImageTransformFlow {
  private final ImageRepository imageRepository;
  private final EventPublisher eventPublisher;

  public ImageTransformFlow(ImageRepository imageRepository, EventPublisher eventPublisher) {
    this.imageRepository = imageRepository;
    this.eventPublisher = eventPublisher;
  }

  public ImageTransformation transformImage(String imageId, ImageTransformationRequest transformationRequest, String username) {
    Image image = this.imageRepository.findById(imageId);
    if (!Objects.equals(image.getOwner(), username)) {
      throw new RuntimeException("Access denied");
    }
    validateTransformation(image, transformationRequest);
    if (image.getTransformations() == null) {
      image.setTransformations(new ArrayList<>());
    }
    var existingTransformationsIds = image.getTransformations()
        .stream()
        .map(ImageTransformation::getId)
        .collect(Collectors.toSet());
    UUID generatedId = UUID.randomUUID();
    while (existingTransformationsIds.contains(generatedId)) {
      generatedId = UUID.randomUUID();
    }
    LocalDateTime now = LocalDateTime.now();
    ImageTransformation imageTransformation = new ImageTransformation(
        generatedId,
        transformationRequest.resize(),
        transformationRequest.crop(),
        transformationRequest.rotate(),
        transformationRequest.format(),
        transformationRequest.filters(),
        now,
        now,
        username,
        username,
        username
    );
    imageTransformation.setStatus(TransformationStatus.REQUESTED);
    image.getTransformations().add(imageTransformation);
    this.imageRepository.update(image);
    this.eventPublisher.publishEvent(
        new TransformationRequestEvent(
            image.getId(),
            imageTransformation.getId()
        )
    );
    return imageTransformation;
  }

  private void validateTransformation(Image image, ImageTransformationRequest transformation) {
    // TODO: Validate transformation request again the metadata of the request
    //    whether the transfomration is possible of not
  }
}
