package dev.javarush.backend_projects.personal_blog.db;

import java.util.UUID;

public class InMemoryUniqueIdGenerator implements UniqueIdGenerator{
  @Override
  public UUID getNextUniqueId() {
    return UUID.randomUUID();
  }
}
