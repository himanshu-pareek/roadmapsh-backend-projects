package dev.javarush.roadmapsh.image_processing.core.processor;

import dev.javarush.roadmapsh.image_processing.core.ImageMetadata;
import java.io.InputStream;

public interface ImageMetadataService {
  ImageMetadata computeMetadata(InputStream image);
}
