package dev.javarush.roadmapsh_projects.markdown_notes.converter;

public class ConverterException extends RuntimeException {
  public ConverterException(String message) {
    super(message);
  }

  public ConverterException(String message, Throwable cause) {
    super(message, cause);
  }
}
