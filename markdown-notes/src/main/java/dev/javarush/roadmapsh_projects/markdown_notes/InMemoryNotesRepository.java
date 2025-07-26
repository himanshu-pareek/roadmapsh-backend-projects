package dev.javarush.roadmapsh_projects.markdown_notes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryNotesRepository implements NotesRepository{

  private final Map<UUID, Note> notes;

  public InMemoryNotesRepository() {
    this.notes = new ConcurrentHashMap<>();
  }

  @Override
  public Note saveNote(String name) {
    UUID id = createUniqueId();
    while (notes.containsKey(id)) {
      id = createUniqueId();
    }
    Note note = new Note(id, name);
    this.notes.put(id, note);
    return note;
  }

  @Override
  public Collection<Note> getAllNotes() {
    return this.notes.values();
  }

  @Override
  public Note getNote(UUID id) {
    if (!this.notes.containsKey(id)) {
      throw new NoteNotFoundException(id);
    }
    return this.notes.get(id);
  }

  @Override
  public void removeNote(UUID id) {
    this.notes.remove(id);
  }

  private UUID createUniqueId() {
    return UUID.randomUUID();
  }
}
