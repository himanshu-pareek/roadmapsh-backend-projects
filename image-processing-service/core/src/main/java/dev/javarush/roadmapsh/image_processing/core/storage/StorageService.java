package dev.javarush.roadmapsh.image_processing.core.storage;

import java.io.InputStream;

public interface StorageService {
  String store(InputStream content);

  /**
   * Read the object(file) specified by `objectId` and returns an input stream
   * <b>User is responsible to close the stream</b>
   * @param objectId id returned by `store` method while saving the object (file)
   * @return an InputStream of the content stored in the files specified using `objectId`
   */
  InputStream retrieve(String objectId);
}
