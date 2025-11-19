package dev.javarush.roadmapsh.image_processing.api.flows;

import dev.javarush.roadmapsh.image_processing.core.Image;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import dev.javarush.roadmapsh.image_processing.core.TransformationStatus;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.storage.FileObject;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;
import org.springframework.security.core.parameters.P;

public class ImageGetFlow {
  private final ImageRepository imageRepository;
  private final StorageService storageService;

  public ImageGetFlow(ImageRepository imageRepository, StorageService storageService) {
    this.imageRepository = imageRepository;
    this.storageService = storageService;
  }

  public Collection<Image> getImagesForUser(String username) {
    return imageRepository.findByOwner(username);
  }

  public FileObject downloadImage(
      String imageId,
      String transformationId
  ) {
    Image image = this.imageRepository.findById(imageId);
    String objectId = image.getObjectRef();
    if (transformationId != null) {
      ImageTransformation transformation = image.getTransformations()
          .stream()
          .filter(t -> t.getId().toString().equals(transformationId))
          .findFirst()
          .orElseThrow(
              () -> new IllegalArgumentException("Invalid transformationId: " + transformationId));
      if (transformation.getStatus() != TransformationStatus.COMPLETED) {
        throw new IllegalArgumentException("Transformation is not ready to download");
      }
      objectId = transformation.getObjectRef();
    }
    return this.storageService.retrieve(objectId);
  }
}
