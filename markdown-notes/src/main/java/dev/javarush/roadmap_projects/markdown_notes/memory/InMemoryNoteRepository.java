package dev.javarush.roadmap_projects.markdown_notes.memory;

import dev.javarush.roadmap_projects.markdown_notes.core.Note;
import dev.javarush.roadmap_projects.markdown_notes.core.NoteRepository;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryNoteRepository implements NoteRepository {

  private final Map<UUID, Note> notes;

  public InMemoryNoteRepository() {
    notes = new ConcurrentHashMap<>();
  }

  @Override
  public Note save(Note note) {
    UUID uuid = newUUID();
    while(this.notes.containsKey(uuid)) {
      uuid = newUUID();
    }
    this.notes.put(uuid, note.withId(uuid));
    return this.notes.get(uuid);
  }

  @Override
  public Collection<Note> getAll() {
    return this.notes.values();
  }

  @Override
  public Note getNote(UUID id) {
    if (!this.notes.containsKey(id)) {
      throw new RuntimeException("Not not found " + id);
    }
    return this.notes.get(id);
  }

  private UUID newUUID() {
    return UUID.randomUUID();
  }
}
