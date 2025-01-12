package dev.javarush.backend_projects.personal_blog.article.web;

import java.time.LocalDate;

import dev.javarush.backend_projects.personal_blog.article.CreateArticleParameters;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateArticleFormData {
    @NotNull
    @Size(min = 5, max = 100)
    private String title;
    @NotNull
    private LocalDate publishDate;
    @NotNull
    @Size(min = 20, max = 10000)
    private String content;

    public CreateArticleFormData() {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CreateArticleFormData{" +
                "title='" + title + '\'' +
                ", publishDate=" + publishDate +
                ", content='" + content + '\'' +
                '}';
    }

    public CreateArticleParameters toParameters() {
        return new CreateArticleParameters(this.title, this.publishDate, this.content);
    }

}
