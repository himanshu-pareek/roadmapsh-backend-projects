package dev.javarush.roadmapsh_projects.markdown_notes;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NotesRepository {
  Note saveNote(String name);

  Collection<Note> getAllNotes();

  Note getNote(UUID id);

  void removeNote(UUID id);
}
