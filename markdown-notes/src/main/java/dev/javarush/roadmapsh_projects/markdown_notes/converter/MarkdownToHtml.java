package dev.javarush.roadmapsh_projects.markdown_notes.converter;

import java.io.InputStream;
import java.io.OutputStream;

public interface MarkdownToHtml {
  void convert(InputStream markdown, OutputStream html);
}
