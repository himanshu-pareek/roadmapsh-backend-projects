package dev.javarush.roadmapsh.image_processing.opencv;

import dev.javarush.roadmapsh.image_processing.core.ImageCropTransformation;
import dev.javarush.roadmapsh.image_processing.core.ImageResizeTransformation;
import dev.javarush.roadmapsh.image_processing.core.ImageTransformation;
import dev.javarush.roadmapsh.image_processing.core.processor.ImageTransformer;
import dev.javarush.roadmapsh.image_processing.core.storage.FileObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OpenCVImageTransformer implements ImageTransformer {

  static {
    OpenCV.loadShared();
  }

  @Override
  public FileObject transformImage(FileObject fileObject, ImageTransformation transformation)
      throws IOException {
    // 0. Read image in OpenCV Matrix form
    Path file = Files.createTempFile(
        null,
        ".PNG",
        PosixFilePermissions.asFileAttribute(
            PosixFilePermissions.fromString("rw-------")
        )
    );
    Files.copy(fileObject.content(), file, StandardCopyOption.REPLACE_EXISTING);
    Mat image = Imgcodecs.imread(file.toAbsolutePath().toString());

    // 1. Crop
    ImageCropTransformation crop = transformation.getCrop();
    if (crop != null) {
      int cropHeight = crop.height();
      int cropWidth = crop.width();
      int cropX = crop.x();
      int cropY = crop.y();
      Rect cropRectangle = new Rect(cropX, cropY, cropWidth, cropHeight);
      image = new Mat(image, cropRectangle);
    }


    // 2. Resize
    ImageResizeTransformation resize = transformation.getResize();
    if (resize != null) {
      Mat resized = new Mat();
      Imgproc.resize(image, resized, new Size(resize.width(), resize.height()));
      image = resized;
    }

    // 3. Rotate
    int rotateCode = switch (transformation.getRotate()) {
      case 90 -> Core.ROTATE_90_CLOCKWISE;
      case 180 -> Core.ROTATE_180;
      case -90, 270 -> Core.ROTATE_90_COUNTERCLOCKWISE;
      default -> -1;
    };
    if (rotateCode != -1) {
      Mat rotated;
      if (rotateCode == Core.ROTATE_180) {
        rotated = new Mat(image.rows(), image.cols(), image.type());
      } else {
        rotated = new Mat(image.cols(), image.rows(), image.type());
      }
      Core.rotate(image, rotated, rotateCode);
      image = rotated;
    }

    // 4. Re-format

    // 5. Filter
    // 5.1. Gray-scale

    // 5.2. Sepia

    // FINAL - Create input stream of the file
    Imgcodecs.imwrite(file.toAbsolutePath().toString(), image);
    return new FileObject(Files.newInputStream(file), file.toFile().length());
  }
}
