package dev.javarush.roadmapsh.image_processing.core;

public enum ImageFormat {
  PNG,
  JPEG,
  WEBP,
  UNKNOWN;

  public static ImageFormat fromString(String format) {
    for (ImageFormat f : ImageFormat.values()) {
      if (f.name().equalsIgnoreCase(format)) {
        return f;
      }
    }
    return UNKNOWN;
  }
}
