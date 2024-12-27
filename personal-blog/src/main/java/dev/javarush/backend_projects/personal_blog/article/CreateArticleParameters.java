package dev.javarush.backend_projects.personal_blog.article;

import java.time.LocalDate;

public record CreateArticleParameters(
        String title,
        LocalDate publishData,
        String content) {
}
