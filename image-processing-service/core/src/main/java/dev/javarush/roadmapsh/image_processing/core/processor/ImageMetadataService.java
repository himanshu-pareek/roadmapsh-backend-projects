package dev.javarush.roadmapsh.image_processing.core.processor;

import dev.javarush.roadmapsh.image_processing.core.ImageMetadata;
import dev.javarush.roadmapsh.image_processing.core.storage.FileObject;
import java.io.InputStream;

public interface ImageMetadataService {
  ImageMetadata computeMetadata(FileObject image);
}
