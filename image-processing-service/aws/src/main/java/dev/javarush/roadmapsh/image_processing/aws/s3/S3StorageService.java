package dev.javarush.roadmapsh.image_processing.aws.s3;

import dev.javarush.roadmapsh.image_processing.core.storage.FileObject;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Component
@Profile("aws-s3")
public class S3StorageService implements StorageService {
  private final S3Client s3Client;

  @Value("${AWS_S3_BUCKET}")
  private String bucket;

  public S3StorageService(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  @Override
  public FileObject retrieve(String objectId) {
    ResponseInputStream<GetObjectResponse> object = this.s3Client.getObject(GetObjectRequest
        .builder()
        .bucket(bucket)
        .key(objectId)
        .build());
    return new FileObject(object, object.response().contentLength());
  }

  public String store(FileObject file) {
    String objectId = generateKey();
    this.s3Client.putObject(
        PutObjectRequest.builder()
            .bucket(bucket)
            .key(objectId)
            .build(),
        RequestBody.fromInputStream(file.content(), file.size())
    );
    return objectId;
  }

  private String generateKey() {
    return UUID.randomUUID().toString();
  }
}
