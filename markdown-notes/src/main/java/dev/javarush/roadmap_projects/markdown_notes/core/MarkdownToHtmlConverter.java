package dev.javarush.roadmap_projects.markdown_notes.core;

import java.io.InputStream;
import java.io.OutputStream;

public interface MarkdownToHtmlConverter {
  void convert(InputStream markdown, OutputStream html);
}
