package dev.javarush.roadmap_projects.markdown_notes.filesystem;

import dev.javarush.roadmap_projects.markdown_notes.core.StorageService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FilesStorageService implements StorageService {

  private final Path rootLocation;

  public FilesStorageService(String rootPath) {
    this.rootLocation = Paths.get(rootPath).toAbsolutePath();
  }

  @Override
  public String store(InputStream content) {
    String objectId = generateId();
    Path fileLocation = getFileLocation(objectId);
    try {
      Files.copy(content, fileLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return objectId;
  }

  @Override
  public InputStream retrieve(String objectId) {
    Path fileLocation = getFileLocation(objectId);
    try {
      return Files.newInputStream(fileLocation);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String generateId() {
    return UUID.randomUUID().toString();
  }

  private Path getFileLocation(String filePath) {
    Path fileLocation = this.rootLocation.resolve(filePath).normalize().toAbsolutePath();
    if (!fileLocation.getParent().equals(this.rootLocation)) {
      throw new RuntimeException("Can not create file at " + filePath);
    }
    return fileLocation;
  }
}
