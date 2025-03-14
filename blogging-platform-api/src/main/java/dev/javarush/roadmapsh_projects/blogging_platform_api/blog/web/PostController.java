package dev.javarush.roadmapsh_projects.blogging_platform_api.blog.web;

import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.Post;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.PostService;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto.CreatePostParameters;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto.PostList;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto.UpdatePostParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(code = org.springframework.http.HttpStatus.CREATED)
    public Post create(@Validated @RequestBody CreatePostParameters parameters) {
        return this.postService.createPost(parameters);
    }

    @GetMapping("{id}")
    public Post getOne(@PathVariable(value = "id", required = true) Integer id) {
        return this.postService.getPost(id);
    }

    @GetMapping
    public PostList getAll(@RequestParam(value = "term", required = false) String term) {
        term = cleanSearchTerm(term);
        var posts = term == null ? postService.getAllPosts() : postService.searchPosts(term);
        return new PostList(posts);
    }

    @PatchMapping("{id}")
    @ResponseStatus(code = org.springframework.http.HttpStatus.OK)
    public Post update(
            @PathVariable(value = "id", required = true) Integer id,
            @Validated @RequestBody UpdatePostParameters parameters
    ) {
        return this.postService.updatePost(id, parameters);
    }

    @DeleteMapping("{id}")
    public void deletePost(@PathVariable(value = "id", required = true) Integer id) {
        this.postService.deletePost(id);
    }

    private String cleanSearchTerm(String term) {
        if (term == null) {
            return null;
        }
        term = term.trim();
        if (term.isEmpty()) {
            return null;
        }
        return term;
    }
}
