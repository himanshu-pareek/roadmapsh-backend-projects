package dev.javarush.backend_projects.personal_blog.article.web;

import java.util.UUID;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import dev.javarush.backend_projects.personal_blog.article.ArticleId;

@Component
public class StringToArticleIdConverter implements Converter<String, ArticleId> {

    @Override
    public ArticleId convert(String id) {
        return new ArticleId(UUID.fromString(id));
    }
}
