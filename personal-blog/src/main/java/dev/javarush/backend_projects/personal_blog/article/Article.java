package dev.javarush.backend_projects.personal_blog.article;

import java.time.LocalDate;

import dev.javarush.backend_projects.personal_blog.article.util.ArticleUtil;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.persistence.Column;

@Entity(name = "Article")
@Table(name = "articles")
public class Article {
  @EmbeddedId
  @AttributeOverride(name = "id", column = @Column(name = "id"))
  private ArticleId id;

  @NotNull
  private String title;

  @NotNull
  private LocalDate publishDate;

  @NotNull
  private String content;

  @Version
  private long version;

  public Article() {
  }

  public Article(ArticleId id, String title, LocalDate publishDate, String content) {
    this.id = id;
    this.title = title;
    this.publishDate = publishDate;
    this.content = content;
  }

  public ArticleId getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public LocalDate getPublishDate() {
    return publishDate;
  }

  public String getContent() {
    return content;
  }

  public long getVersion() {
    return version;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setPublishDate(LocalDate publishDate) {
    this.publishDate = publishDate;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setVersion(long version) {
    this.version = version;
  }

  public String getPublishDateToDisplay() {
    return ArticleUtil.formatDate(publishDate);
  }

  @Override
  public String toString() {
    return "Article{" +
        "id=" + id +
        ", title='" + title + '\'' +
        ", publishDate='" + publishDate + '\'' +
        ", content='" + content + '\'' +
        ", version=" + version +
        '}';
  }
}
