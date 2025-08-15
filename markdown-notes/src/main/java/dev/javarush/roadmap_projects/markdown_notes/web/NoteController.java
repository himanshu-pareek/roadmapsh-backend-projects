package dev.javarush.roadmap_projects.markdown_notes.web;

import dev.javarush.roadmap_projects.markdown_notes.core.Note;
import dev.javarush.roadmap_projects.markdown_notes.core.NoteService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

  private final NoteService noteService;

  public NoteController(NoteService noteService) {
    this.noteService = noteService;
  }

  @PostMapping
  public Note saveNote(@RequestParam("title") String title, @RequestParam("file")MultipartFile file) {
    try (InputStream content = file.getInputStream()) {
      return this.noteService.saveNote(title, content);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @GetMapping
  public Collection<Note> listNotes() {
    return this.noteService.getAllNotes();
  }

  @GetMapping("{id}/render")
  public void renderNote(@PathVariable("id")UUID id, HttpServletResponse response) {
    response.setContentType("text/html");
    try(ServletOutputStream html = response.getOutputStream()) {
      this.noteService.renderNote(id, html);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
