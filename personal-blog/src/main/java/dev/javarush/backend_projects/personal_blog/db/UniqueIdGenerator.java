package dev.javarush.backend_projects.personal_blog.db;

import java.util.UUID;

public interface UniqueIdGenerator {
  UUID getNextUniqueId();
}
