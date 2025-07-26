package dev.javarush.roadmapsh_projects.markdown_notes;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Function;

public interface StorageService {
  /**
   * Store the content from given [inputStream] and return an id, which can be used later
   * to get back the file.
   * @param fileName Name of the target file where we want to store the note
   * @param inputStream stream to save as a file
   * @return id of stored file
   */
  void store(String fileName, InputStream inputStream);

  /**
   * Retrieve the content of file using id and put the content into the given output stream
   * @param fileName The id of the content / file returned by store method
   * @param outputStream The stream to capture the output / content
   */
  void retrieve(String fileName, OutputStream outputStream);

  <T> T process(String fileName, Function<InputStream, T> processor);
}
