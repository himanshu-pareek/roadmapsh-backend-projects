package dev.javarush.roadmapsh.image_processing.filesystem;

import dev.javarush.roadmapsh.image_processing.core.storage.StorageException;
import dev.javarush.roadmapsh.image_processing.core.storage.StorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileSystemStorageService implements StorageService {
  private final Path rootLocation;

  public FileSystemStorageService(String rootPath) {
    this.rootLocation = Paths.get(rootPath).toAbsolutePath();
    if (!Files.exists(this.rootLocation)) {
      try {
        Files.createDirectories(this.rootLocation);
      } catch (IOException e) {
        throw new StorageException(e);
      }
    }
  }

  @Override
  public String store(InputStream content) {
    String objectId = generateId();
    Path fileLocation = getFileLocation(objectId);
    System.out.println("Storing file at " + fileLocation);
    try {
      Files.copy(content, fileLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new StorageException(e);
    }
    return objectId;
  }

  @Override
  public InputStream retrieve(String objectId) {
    Path fileLocation = getFileLocation(objectId);
    System.out.println("Getting file from " + fileLocation);
    try {
      return Files.newInputStream(fileLocation);
    } catch (IOException e) {
      throw new StorageException(e);
    }
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }

  private Path getFileLocation(String filePath) {
    Path fileLocation = this.rootLocation.resolve(filePath).normalize().toAbsolutePath();
    if (!fileLocation.getParent().equals(this.rootLocation)) {
      throw new StorageException("Can not create file at " + filePath);
    }
    return fileLocation;
  }
}
