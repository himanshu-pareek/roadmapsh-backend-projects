package dev.javarush.roadmapsh.image_processing.api.web;

import dev.javarush.roadmapsh.image_processing.api.dto.ImageListResponse;
import dev.javarush.roadmapsh.image_processing.api.dto.ImageResponse;
import dev.javarush.roadmapsh.image_processing.api.dto.ImageTransformationRequest;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageGetFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageTransformFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageUploadFlow;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("images")
class ImageController {

  private final ImageUploadFlow imageUploadFlow;
  private final ImageGetFlow imageGetFlow;
  private final ImageTransformFlow imageTransformFlow;

  ImageController(ImageUploadFlow imageUploadFlow, ImageGetFlow imageGetFlow,
                  ImageTransformFlow imageTransformFlow) {
    this.imageUploadFlow = imageUploadFlow;
    this.imageGetFlow = imageGetFlow;
    this.imageTransformFlow = imageTransformFlow;
  }

  @PostMapping
  ImageResponse uploadImage(
      @RequestParam("title") String title,
      @RequestParam("file") MultipartFile file,
      Authentication authentication
  ) {
    try {
      var image = imageUploadFlow.uploadImage(
          title,
          file.getInputStream(),
          authentication.getName()
      );
      return ImageResponse.fromImage(image);
    } catch (Exception e) {
      throw new RuntimeException("Failed to upload image", e);
    }
  }

  @GetMapping
  ImageListResponse getImages(Authentication authentication) {
    var images = imageGetFlow.getImagesForUser(authentication.getName());
    return ImageListResponse.fromImages(images);
  }

  @PostMapping("{imageId}/transform")
  ImageTransformation transformImage(
      @PathVariable("imageId") String imageId,
      @RequestBody ImageTransformationRequest transformation,
      Authentication authentication
  ) {
    return this.imageTransformFlow.transformImage(
        imageId,
        transformation,
        authentication.getName()
    );
  }
}
