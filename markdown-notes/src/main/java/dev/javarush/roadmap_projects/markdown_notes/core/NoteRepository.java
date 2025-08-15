package dev.javarush.roadmap_projects.markdown_notes.core;

import java.util.Collection;
import java.util.UUID;

public interface NoteRepository {
  Note save(Note note);
  Collection<Note> getAll();
  Note getNote(UUID id);
}
