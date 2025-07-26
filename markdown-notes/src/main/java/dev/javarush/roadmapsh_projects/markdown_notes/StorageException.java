package dev.javarush.roadmapsh_projects.markdown_notes;

import java.io.IOException;

public class StorageException extends RuntimeException {
  public StorageException(String message) {
    super(message);
  }

  public StorageException(String message, IOException cause) {
    super(message, cause);
  }
}
