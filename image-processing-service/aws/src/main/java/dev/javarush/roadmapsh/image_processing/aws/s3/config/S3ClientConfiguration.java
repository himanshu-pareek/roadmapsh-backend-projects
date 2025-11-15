package dev.javarush.roadmapsh.image_processing.aws.s3.config;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

@Configuration
public class S3ClientConfiguration {
  @Value("${spring.cloud.aws.credentials.access-key}")
  private String accessKeyId;
  @Value("${spring.cloud.aws.credentials.secret-key}")
  private String secretAccessKey;
  @Value("${spring.cloud.aws.s3.endpoint}")
  private String awsS3Endpoint;

  @Bean
  S3Client s3Client() {
    AwsBasicCredentials credentials = AwsBasicCredentials.create(
            accessKeyId,
            secretAccessKey
        );

        S3Configuration serviceConfiguration = S3Configuration.builder()
            .pathStyleAccessEnabled(true)
            .build();

        return S3Client.builder()
            .endpointOverride(URI.create(awsS3Endpoint))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .region(Region.of("auto"))
            .serviceConfiguration(serviceConfiguration)
            .build();
  }
}
