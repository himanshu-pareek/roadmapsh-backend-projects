package dev.javarush.backend_projects.personal_blog.article;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(ArticleId id) {
        super("Article not found: " + id.asString());
    }
}
