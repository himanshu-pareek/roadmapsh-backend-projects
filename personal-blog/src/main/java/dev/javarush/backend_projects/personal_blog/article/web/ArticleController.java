package dev.javarush.backend_projects.personal_blog.article.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.javarush.backend_projects.personal_blog.article.Article;
import dev.javarush.backend_projects.personal_blog.article.ArticleService;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String all(Model model) {
        Iterable<Article> articles = this.articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "articles/index";
    }
}
