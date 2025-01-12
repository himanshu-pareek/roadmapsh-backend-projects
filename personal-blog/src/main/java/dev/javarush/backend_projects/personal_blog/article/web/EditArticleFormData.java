package dev.javarush.backend_projects.personal_blog.article.web;

import dev.javarush.backend_projects.personal_blog.article.Article;
import dev.javarush.backend_projects.personal_blog.article.UpdateArticleParameters;

public class EditArticleFormData extends ArticleFormData {
    private String id;
    private long version;

    public String getId() {
        return id;
    }

    public long getVersion() {
        return version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public static Object fromArticle(Article article) {
        var formData = new EditArticleFormData();
        formData.id = article.getId().asString();
        formData.version = article.getVersion();
        formData.setTitle(article.getTitle());
        formData.setPublishDate(article.getPublishDate());
        formData.setContent(article.getContent());
        return formData;
    }

    public UpdateArticleParameters toParameters() {
        return new UpdateArticleParameters(getTitle(), getPublishDate(), getContent(), getVersion());
    }
}
