package dev.javarush.roadmapsh.image_processing.processor;

import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.ImageMetadata;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import dev.javarush.roadmapsh.image_processing.core.TransformationStatus;
import dev.javarush.roadmapsh.image_processing.core.event.EventConsumer;
import dev.javarush.roadmapsh.image_processing.core.event.ImageUploadEvent;
import dev.javarush.roadmapsh.image_processing.core.event.TransformationRequestEvent;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageMetadataService;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageTransformer;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageService implements EventConsumer {

  private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

  private final ImageRepository repository;
  private final StorageService storageService;
  private final ImageMetadataService metadataService;
  private final ImageTransformer imageTransformer;

  public ImageService(
      ImageRepository repository,
      StorageService storageService,
      ImageMetadataService metadataService,
      ImageTransformer imageTransformer
  ) {
    this.repository = repository;
    this.storageService = storageService;
    this.metadataService = metadataService;
    this.imageTransformer = imageTransformer;
  }

  @Override
  public void consumeEvent(ImageUploadEvent event) {
    logger.info("Received image upload event");
    UUID imageId = event.imageId();
    logger.info("Image ID to process: {}", imageId);
    Image image = this.repository.findById(imageId.toString());
    logger.info("Image retrieved from database: {}", image);
    String objectRef = image.getObjectRef();
    try (InputStream imageStream = this.storageService.retrieve(objectRef)) {
      ImageMetadata imageMetadata = this.metadataService.computeMetadata(imageStream);
      image.setMetadata(imageMetadata);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.repository.update(image);
  }

  @Override
  public void consumeEvent(TransformationRequestEvent event) {
    String imageId = event.imageId();
    UUID transformationId = event.transformationId();
    Image image = this.repository.findById(imageId);
    if (image.getTransformations() == null) {
      logger.warn("Invalid transformation {} for image {}", transformationId, imageId);
      return;
    }
    Optional<ImageTransformation> first = image.getTransformations()
        .stream()
        .filter(t -> t.getId().equals(transformationId))
        .findFirst();
    if (first.isEmpty()) {
      logger.warn("Invalid transformation {} for image {}", transformationId, imageId);
      return;
    }
    ImageTransformation transformation = first.get();
    if (transformation.getStatus() != TransformationStatus.REQUESTED) {
      logger.warn("Transformation is in state {}. Not continuing with it",
          transformation.getStatus().toString());
      return;
    }
    transformation.setStatus(TransformationStatus.IN_PROGRESS);
    this.repository.update(image);
    try (
        InputStream imageStream = this.storageService.retrieve(image.getObjectRef());
        InputStream transformed = this.imageTransformer.transformImage(imageStream, transformation)
    ) {
      String objectRef = this.storageService.store(transformed);
      transformation.setObjectRef(objectRef);
      transformation.setMessage("Completed successfully");
      transformation.setStatus(TransformationStatus.COMPLETED);
    } catch (Throwable e) {
      logger.error("Error transforming image: {}", e.getMessage());
      transformation.setStatus(TransformationStatus.FAILED);
      transformation.setMessage(e.getMessage());
    }
    this.repository.update(image);
  }
}
