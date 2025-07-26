package dev.javarush.roadmapsh_projects.markdown_notes.web;

import dev.javarush.roadmapsh_projects.markdown_notes.Note;
import dev.javarush.roadmapsh_projects.markdown_notes.NotesRepository;
import dev.javarush.roadmapsh_projects.markdown_notes.StorageService;
import dev.javarush.roadmapsh_projects.markdown_notes.converter.MarkdownToHtml;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

  private static final Logger LOGGER = LoggerFactory.getLogger(NotesController.class);

  private final StorageService storageService;
  private final NotesRepository notesRepository;
  private final MarkdownToHtml markdownToHtml;

  public NotesController(StorageService storageService, NotesRepository notesRepository, MarkdownToHtml markdownToHtml) {
    this.storageService = storageService;
    this.notesRepository = notesRepository;
    this.markdownToHtml = markdownToHtml;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  UUID saveNote(@RequestParam("name") String name, @RequestParam("file")MultipartFile file) throws IOException {
    Note note = this.notesRepository.saveNote(name);
    try (var inputStream = file.getInputStream()) {
      this.storageService.store(note.id().toString(), inputStream);
      return note.id();
    } catch (Throwable e) {
      this.notesRepository.removeNote(note.id());
      throw e;
    }
  }

  @GetMapping
  Collection<Note> getAllNotes() {
    return this.notesRepository.getAllNotes();
  }

  @GetMapping("{id}")
  void getNote(@PathVariable("id") UUID id, HttpServletResponse response) throws IOException {
    Note note = this.notesRepository.getNote(id);
    try (var outputStream = response.getOutputStream()) {
      this.storageService.retrieve(note.id().toString(), outputStream);
      response.setContentType("text/markdown");
    }
  }

  @GetMapping("{id}/render")
  void render(@PathVariable("id") UUID id, HttpServletResponse response) throws IOException {
    Note note = this.notesRepository.getNote(id);
    OutputStream outputStream = response.getOutputStream();
    this.storageService.process(note.id().toString(), (Function<InputStream, Void>) inputStream -> {
      markdownToHtml.convert(inputStream, outputStream);
      return null;
    });
    response.setContentType("text/html");
  }
}
