package dev.javarush.roadmap_projects.markdown_notes.config;

import dev.javarush.roadmap_projects.markdown_notes.core.MarkdownToHtmlConverter;
import dev.javarush.roadmap_projects.markdown_notes.core.NoteRepository;
import dev.javarush.roadmap_projects.markdown_notes.core.NoteService;
import dev.javarush.roadmap_projects.markdown_notes.core.StorageService;
import dev.javarush.roadmap_projects.markdown_notes.filesystem.FilesStorageService;
import dev.javarush.roadmap_projects.markdown_notes.flexmark.FlexmarkMarkdownToHtml;
import dev.javarush.roadmap_projects.markdown_notes.memory.InMemoryNoteRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
  @Bean
  StorageService storageService() {
    return new FilesStorageService("notes-uploads");
  }

  @Bean
  NoteRepository noteRepository() {
    return new InMemoryNoteRepository();
  }

//  @Bean
//  MarkdownToHtmlConverter markdownToHtmlConverter() {
//    return new MarkdownToHtmlConverter() {
//      @Override
//      public void convert(InputStream markdown, OutputStream html) {
//        try {
//          while (true) {
//            int read = markdown.read();
//            if (read == -1) {
//              break;
//            }
//            html.write(read);
//          }
//        } catch (IOException e) {
//          throw new RuntimeException(e);
//        }
//      }
//    };
//  }

  @Bean
  MarkdownToHtmlConverter markdownToHtmlConverter() {
    return new FlexmarkMarkdownToHtml();
  }

  @Bean
  NoteService noteService(StorageService storage, NoteRepository repo, MarkdownToHtmlConverter converter) {
    return new NoteService(repo, storage, converter);
  }
}
