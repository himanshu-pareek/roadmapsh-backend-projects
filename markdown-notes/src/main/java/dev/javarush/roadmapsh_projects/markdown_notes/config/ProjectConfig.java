package dev.javarush.roadmapsh_projects.markdown_notes.config;

import dev.javarush.roadmapsh_projects.markdown_notes.FileStorageService;
import dev.javarush.roadmapsh_projects.markdown_notes.InMemoryNotesRepository;
import dev.javarush.roadmapsh_projects.markdown_notes.NotesRepository;
import dev.javarush.roadmapsh_projects.markdown_notes.StorageService;
import dev.javarush.roadmapsh_projects.markdown_notes.converter.FlexMarkMarkdownToHtml;
import dev.javarush.roadmapsh_projects.markdown_notes.converter.MarkdownToHtml;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
  @Bean
  StorageService fileStorageService() {
    return new FileStorageService("notes");
  }

  @Bean
  MarkdownToHtml markdownToHtml() {
    return new FlexMarkMarkdownToHtml();
  }

  @Bean
  NotesRepository notesRepository() {
    return new InMemoryNotesRepository();
  }
}
