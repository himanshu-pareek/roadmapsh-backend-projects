package dev.javarush.backend_projects.personal_blog.article.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import dev.javarush.backend_projects.personal_blog.article.Article;
import dev.javarush.backend_projects.personal_blog.article.ArticleId;
import dev.javarush.backend_projects.personal_blog.article.ArticleService;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("articles/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editArticleForm(@PathVariable("id") ArticleId id, Model model) {
        Article article = this.articleService.getArticleById(id);
        model.addAttribute("editMode", EditMode.UPDATE);
        model.addAttribute("article", EditArticleFormData.fromArticle(article));
        return "articles/edit";
    }

    @PostMapping("articles/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String updateArticle(
            @PathVariable("id") ArticleId id,
            @Validated @ModelAttribute("article") EditArticleFormData data,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.UPDATE);
            return "articles/edit";
        }
        this.articleService.updateArticle(id, data.toParameters());
        return "redirect:/admin";
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

    @GetMapping("new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createArticleForm(Model model) {
        model.addAttribute("editMode", EditMode.CREATE);
        model.addAttribute("article", new CreateArticleFormData());
        return "articles/edit";
    }

    @PostMapping("articles")
    @PreAuthorize("hasRole('ADMIN')")
    public String createArticle(
            @Validated @ModelAttribute("article") CreateArticleFormData data,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("editMode", EditMode.CREATE);
            return "articles/edit";
        }
        this.articleService.createArticle(data.toParameters());
        return "redirect:/admin";
    }

}
