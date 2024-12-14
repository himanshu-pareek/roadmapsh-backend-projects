package dev.javarush.backend_projects.personal_blog.article;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleService {
  private final ArticleRepository repository;

  public ArticleService(ArticleRepository repository) {
    this.repository = repository;
  }

  public Article createArticle(CreateArticleParameters parameters) {
    ArticleId articleId = this.repository.nextId();
    Article article = new Article(
        articleId,
        parameters.title(),
        parameters.publishData(),
        parameters.content()
    );
    return this.repository.save(article);
  }
}
