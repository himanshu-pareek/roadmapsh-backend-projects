package dev.javarush.roadmapsh.image_processing.core.processor;

import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import java.io.IOException;
import java.io.InputStream;

public interface ImageTransformer {
  InputStream transformImage(InputStream imageStream, ImageTransformation transformation)
      throws IOException;
}
