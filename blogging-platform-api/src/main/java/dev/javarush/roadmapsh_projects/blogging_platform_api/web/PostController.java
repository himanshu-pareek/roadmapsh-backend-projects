package dev.javarush.roadmapsh_projects.blogging_platform_api.web;

import dev.javarush.roadmapsh_projects.blogging_platform_api.Post;
import dev.javarush.roadmapsh_projects.blogging_platform_api.PostService;
import dev.javarush.roadmapsh_projects.blogging_platform_api.dto.CreatePostParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post create(@Validated @RequestBody CreatePostParameters parameters) {
        return this.postService.save(parameters);
    }
}
