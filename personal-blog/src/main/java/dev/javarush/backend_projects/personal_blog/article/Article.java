package dev.javarush.backend_projects.personal_blog.article;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity(name = "Article")
@Table(name = "articles")
public class Article {
  @EmbeddedId
  @AttributeOverride(
      name = "id",
      column = @Column(name = "id")
  )
  private ArticleId id;

  @NotNull
  private String title;

  @NotNull
  private LocalDate publishDate;

  @NotNull
  private String content;

  @Version
  private long version;

  public Article() {}

  public Article(ArticleId id, String title, LocalDate publishDate, String content) {
    this.id = id;
    this.title = title;
    this.publishDate = publishDate;
    this.content = content;
  }

  public ArticleId getId() {
    return id;
  }

  public @NotNull String getTitle() {
    return title;
  }

  public void setTitle(@NotNull String title) {
    this.title = title;
  }

  public @NotNull LocalDate getPublishDate() {
    return publishDate;
  }

  public void setPublishDate(@NotNull LocalDate publishDate) {
    this.publishDate = publishDate;
  }

  public @NotNull String getContent() {
    return content;
  }

  public void setContent(@NotNull String content) {
    this.content = content;
  }

  public long getVersion() {
    return version;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", publishDate=" + publishDate +
        ", content='" + content + '\'' +
        ", version=" + version +
        '}';
  }
}
