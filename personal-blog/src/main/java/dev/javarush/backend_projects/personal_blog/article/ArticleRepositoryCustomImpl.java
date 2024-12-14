package dev.javarush.backend_projects.personal_blog.article;

import dev.javarush.backend_projects.personal_blog.db.UniqueIdGenerator;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom{
  private final UniqueIdGenerator uniqueIdGenerator;

  public ArticleRepositoryCustomImpl(UniqueIdGenerator uniqueIdGenerator) {
    this.uniqueIdGenerator = uniqueIdGenerator;
  }

  @Override
  public ArticleId nextId() {
    return new ArticleId(this.uniqueIdGenerator.getNextUniqueId());
  }
}
