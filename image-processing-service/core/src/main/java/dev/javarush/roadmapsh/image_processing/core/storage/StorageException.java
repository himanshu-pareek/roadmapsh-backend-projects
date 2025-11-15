package dev.javarush.roadmapsh.image_processing.core.storage;

public class StorageException extends RuntimeException {
  public StorageException(Throwable cause) {
    super(cause);
  }

  public StorageException(String message) {
    super(message);
  }
}
