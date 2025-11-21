package dev.javarush.roadmapsh.image_processing.api.config;

import dev.javarush.roadmapsh.image_processing.api.flows.ImageGetFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageTransformFlow;
import dev.javarush.roadmapsh.image_processing.api.flows.ImageUploadFlow;
import dev.javarush.roadmapsh.image_processing.core.event.EventPublisher;
import dev.javarush.roadmapsh.image_processing.core.rate_limiting.RateLimiter;
import dev.javarush.roadmapsh.image_processing.core.repository.ImageRepository;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import dev.javarush.roadmapsh.image_processing.filesystem.FileSystemStorageService;
import dev.javarush.roadmapsh.image_processing.redis.config.RedisConfiguration;
import dev.javarush.roadmapsh.image_processing.redis.config.rate_limit.RedisTokenBucketRateLimiter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "dev.javarush.roadmapsh.image_processing.mongodb",
    "dev.javarush.roadmapsh.image_processing.ampq.producer",
    "dev.javarush.roadmapsh.image_processing.aws.s3"
})
public class ProjectConfig {

  @Value("${redis.host}")
  private String redisHost;

  @Value("${redis.port}")
  private int redisPort;

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

  @Bean
  @Qualifier("default")
  RateLimiter defaultRateLimiter() {
    return new RedisTokenBucketRateLimiter(
        new RedisConfiguration(redisHost, redisPort),
        10,
        1,
        20000
    );
  }

  @Bean
  @Qualifier("global")
  RateLimiter globalRateLimiter(@Qualifier("default") RateLimiter defaultRateLimiter) {
    return defaultRateLimiter;
  }

  @Bean
  @Qualifier("user")
  RateLimiter userRateLimiter(@Qualifier("default") RateLimiter defaultRateLimiter) {
    return defaultRateLimiter;
  }
}
