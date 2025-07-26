package dev.javarush.roadmapsh_projects.markdown_notes;

import java.util.UUID;

public class NoteNotFoundException extends RuntimeException {
  public NoteNotFoundException(UUID id) {
    super("Note not found: " + id.toString());
  }
}
