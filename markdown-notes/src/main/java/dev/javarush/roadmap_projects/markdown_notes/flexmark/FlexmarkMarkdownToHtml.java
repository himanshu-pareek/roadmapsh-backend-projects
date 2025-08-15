package dev.javarush.roadmap_projects.markdown_notes.flexmark;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import dev.javarush.roadmap_projects.markdown_notes.core.MarkdownToHtmlConverter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class FlexmarkMarkdownToHtml implements MarkdownToHtmlConverter {

  private final Parser parser;
  private final HtmlRenderer renderer;

  public FlexmarkMarkdownToHtml(DataHolder configuration) {
    this.parser = Parser.builder(configuration).build();
    this.renderer = HtmlRenderer.builder(configuration).build();
  }

  public FlexmarkMarkdownToHtml() {
    this(new MutableDataSet());
  }

  @Override
  public void convert(InputStream markdown, OutputStream html) {
    try (InputStreamReader reader = new InputStreamReader(markdown);
         OutputStreamWriter writer = new OutputStreamWriter(html)) {
      Document document = this.parser.parseReader(reader);
      this.renderer.render(document, writer, 1);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
