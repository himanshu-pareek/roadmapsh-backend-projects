package dev.javarush.backend_projects.personal_blog.article;

import java.time.LocalDate;

public record UpdateArticleParameters(
        String title,
        LocalDate publishDate,
        String content,
        long version) {

    public void update(Article article) {
        article.setTitle(title);
        article.setPublishDate(publishDate);
        article.setContent(content);
    }

}
