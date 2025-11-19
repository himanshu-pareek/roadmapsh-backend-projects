package dev.javarush.roadmapsh.image_processing.api.config;

import dev.javarush.roadmapsh.image_processing.api.flows.ImageGetFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageTransformFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageUploadFlow;
import dev.javarush.roadmapsh.image_processing.core.event.EventPublisher;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import dev.javarush.roadmapsh.image_processing.filesystem.FileSystemStorageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "dev.javarush.roadmapsh.image_processing.mongodb",
    "dev.javarush.roadmapsh.image_processing.ampq.producer",
    "dev.javarush.roadmapsh.image_processing.aws.s3"
})
public class ProjectConfig {
  @Bean
  @ConditionalOnMissingBean
  StorageService storageService() {
    return new FileSystemStorageService("/Users/himanshu/Projects/roadmapsh-backend-projects/image-processing-service");
  }

  @Bean
  ImageGetFlow imageGetFlow(ImageRepository imageRepository, StorageService storageService) {
    return new ImageGetFlow(imageRepository, storageService);
  }

  @Bean
  ImageUploadFlow imageUploadFlow(
      StorageService storageService,
      ImageRepository imageRepository,
      EventPublisher eventPublisher
  ) {
    return new ImageUploadFlow(
        storageService,
        imageRepository,
        eventPublisher
    );
  }

  @Bean
  ImageTransformFlow imageTransformFlow(
      ImageRepository imageRepository,
      EventPublisher eventPublisher
  ) {
    return new ImageTransformFlow(
        imageRepository,
        eventPublisher
    );
  }
}
