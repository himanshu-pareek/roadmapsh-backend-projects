package dev.javarush.backend_projects.personal_blog.article;

import java.util.UUID;
import org.springframework.util.Assert;

public record ArticleId(UUID id) {
  public ArticleId {
    Assert.notNull(id, "id must not be null");
  }

  public ArticleId() {
    this(UUID.randomUUID());
  }

  public String asString() {
    return id.toString();
  }
}
