package dev.javarush.roadmapsh.image_processing.processor;

import dev.javarush.roadmapsh.image_processing.core.ImageFormat;
import dev.javarush.roadmapsh.image_processing.core.ImageMetadata;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageMetadataService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class DefaultImageMetadataService implements ImageMetadataService {
  @Override
  public ImageMetadata computeMetadata(InputStream imageStream) {
    byte[] imageBytes = null;
    try {
      imageBytes = imageStream.readAllBytes();
      long size = imageBytes.length;

      try (ByteArrayInputStream baisForFormat = new ByteArrayInputStream(imageBytes);
           ImageInputStream iis = ImageIO.createImageInputStream(baisForFormat)) {

        Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
        if (!readers.hasNext()) {
          throw new IOException("No suitable ImageReader found!");
        }

        ImageReader reader = readers.next();
        reader.setInput(iis, true);
        String format = reader.getFormatName();

        int width = reader.getWidth(0);
        int height = reader.getHeight(0);

        reader.dispose();

        return new ImageMetadata(
            height,
            width,
            ImageFormat.fromString(format),
            size
        );
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
