package dev.javarush.roadmapsh.image_processing.core.storage;

import java.io.InputStream;

public record FileObject(InputStream content, long size) implements AutoCloseable {
  @Override
  public void close() {
    try {
      this.content.close();
    } catch (Exception e) {
      System.out.println("Exception in closing: " + e.getMessage());
      // ignore
    }
  }
}
