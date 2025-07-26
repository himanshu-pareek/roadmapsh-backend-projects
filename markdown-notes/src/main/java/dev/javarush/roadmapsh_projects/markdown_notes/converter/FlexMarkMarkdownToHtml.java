package dev.javarush.roadmapsh_projects.markdown_notes.converter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FlexMarkMarkdownToHtml implements MarkdownToHtml {
  private final Parser parser;
  private final HtmlRenderer renderer;

  public FlexMarkMarkdownToHtml(DataHolder options) {
    parser = Parser.builder(options).build();
    renderer = HtmlRenderer.builder(options).build();
  }

  public FlexMarkMarkdownToHtml() {
    this(new MutableDataSet());
  }

  @Override
  public void convert(InputStream markdown, OutputStream html) {
    try (InputStreamReader reader = new InputStreamReader(markdown);
         OutputStreamWriter writer = new OutputStreamWriter(html)) {
      Document parsedDocument = parser.parseReader(reader);
      renderer.render(parsedDocument, writer);
    } catch (IOException e) {
      throw new ConverterException("Unable to convert from markdown to html", e);
    }
  }
}
