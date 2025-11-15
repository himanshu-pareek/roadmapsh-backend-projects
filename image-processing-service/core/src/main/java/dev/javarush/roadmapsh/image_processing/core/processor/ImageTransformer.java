package dev.javarush.roadmapsh.image_processing.core.processor;

import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import dev.javarush.roadmapsh.image_processing.core.storage.FileObject;
import java.io.IOException;
import java.io.InputStream;

public interface ImageTransformer {
  FileObject transformImage(FileObject file, ImageTransformation transformation)
      throws IOException;
}
