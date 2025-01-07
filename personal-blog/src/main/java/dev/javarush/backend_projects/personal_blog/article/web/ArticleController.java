package dev.javarush.backend_projects.personal_blog.article.web;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.javarush.backend_projects.personal_blog.article.Article;
import dev.javarush.backend_projects.personal_blog.article.ArticleId;
import dev.javarush.backend_projects.personal_blog.article.ArticleService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Article> articles = this.articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "articles/index";
    }

    @GetMapping("articles/{id}")
    public String singleArticle(@PathVariable("id") ArticleId id, Model model) {
        Article article = this.articleService.getArticleById(id);
        model.addAttribute("article", article);
        return "articles/article";
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminPage(Model model) {
        var articles = this.articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "articles/admin";
    }

}
