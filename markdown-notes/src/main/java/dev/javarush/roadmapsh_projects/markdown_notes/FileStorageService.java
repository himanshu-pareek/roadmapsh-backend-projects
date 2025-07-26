package dev.javarush.roadmapsh_projects.markdown_notes;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.function.Function;

public class FileStorageService implements StorageService {

  private final Path rootLocation;

  public FileStorageService(String rootPath) {
    rootLocation = Paths.get(rootPath);
  }

  @Override
  public void store(String fileName, InputStream inputStream) {
    Path fileLocation = getFileLocation(fileName);
    try {
      Files.copy(inputStream, fileLocation, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      throw new StorageException("Failed to store file", e);
    }
  }

  @Override
  public void retrieve(String fileName, OutputStream outputStream) {
    Path fileLocation = getFileLocation(fileName);
    try {
      Files.copy(fileLocation, outputStream);
    } catch (IOException e) {
      throw new StorageException("Failed to read file", e);
    }
  }

  @Override
  public <T> T process(String fileName, Function<InputStream, T> processor) {
    Path fileLocation = getFileLocation(fileName);
    try (var inputStream = Files.newInputStream(fileLocation)) {
      return processor.apply(inputStream);
    } catch (IOException e) {
      throw new StorageException("Failed to read file", e);
    }
  }

  private Path getFileLocation(String id) {
    Path fileLocation = this.rootLocation.resolve(id).normalize().toAbsolutePath();
    if (!fileLocation.getParent().equals(this.rootLocation.toAbsolutePath())) {
      throw new StorageException("Cannot store file outside current directory.");
    }
    return fileLocation;
  }
}
