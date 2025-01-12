package dev.javarush.backend_projects.personal_blog.article.web;

import dev.javarush.backend_projects.personal_blog.article.CreateArticleParameters;

public class CreateArticleFormData extends ArticleFormData {
    public CreateArticleParameters toParameters() {
        return new CreateArticleParameters(getTitle(), getPublishDate(), getContent());
    }
}
