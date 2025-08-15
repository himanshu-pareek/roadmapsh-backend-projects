package dev.javarush.roadmap_projects.markdown_notes.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.UUID;

public class NoteService {
  private final NoteRepository noteRepository;
  private final StorageService storageService;
  private final MarkdownToHtmlConverter markdownToHtmlConverter;

  public NoteService(NoteRepository noteRepository, StorageService storageService,
                     MarkdownToHtmlConverter markdownToHtmlConverter) {
    this.noteRepository = noteRepository;
    this.storageService = storageService;
    this.markdownToHtmlConverter = markdownToHtmlConverter;
  }

  public Note saveNote(String title, InputStream content) {
    String objectId = this.storageService.store(content);
    Note note = new Note(null, title, objectId);
    return this.noteRepository.save(note);
  }

  public Collection<Note> getAllNotes() {
    return this.noteRepository.getAll();
  }

  public void renderNote(UUID id, OutputStream html) {
    Note note = this.noteRepository.getNote(id);
    try (InputStream markdown = this.storageService.retrieve(note.objectId())) {
      this.markdownToHtmlConverter.convert(markdown, html);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
