package dev.javarush.roadmapsh.image_processing.processor.config;

import dev.javarush.roadmapsh.image_processing.core.event.EventConsumer;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import dev.javarush.roadmapsh.image_processing.filesystem.FileSystemStorageService;
import dev.javarush.roadmapsh.image_processing.opencv.OpenCVImageTransformer;
import dev.javarush.roadmapsh.image_processing.processor.DefaultImageMetadataService;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageMetadataService;
import dev.javarush.roadmapsh.image_processing.processor.ImageService;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageTransformer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "dev.javarush.roadmapsh.image_processing.mongodb",
    "dev.javarush.roadmapsh.image_processing.amqp.consumer",
    "dev.javarush.roadmapsh.image_processing.aws.s3"
})
public class ProjectConfig {
  @Bean
  @ConditionalOnMissingBean
  StorageService storageService() {
    return new FileSystemStorageService("/Users/himanshu/Projects/roadmapsh-backend-projects/image-processing-service/oss");
  }

  @Bean
  ImageMetadataService imageMetadataService() {
    return new DefaultImageMetadataService();
  }

  @Bean
  ImageTransformer imageTransformer() {
    return new OpenCVImageTransformer();
  }

  @Bean
  EventConsumer eventConsumer(
      ImageRepository imageRepository,
      StorageService storageService,
      ImageMetadataService imageMetadataService,
      ImageTransformer imageTransformer
  ) {
    return new ImageService(
        imageRepository,
        storageService,
        imageMetadataService,
        imageTransformer
    );
  }
}
