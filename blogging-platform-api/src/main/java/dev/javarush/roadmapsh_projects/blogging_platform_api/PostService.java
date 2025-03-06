package dev.javarush.roadmapsh_projects.blogging_platform_api;

import dev.javarush.roadmapsh_projects.blogging_platform_api.dto.CreatePostParameters;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(CreatePostParameters parameters) {
        Post post = new Post(
                parameters.title(),
                parameters.content(),
                parameters.category(),
                parameters.tags()
        );
        return postRepository.save(post);
    }
}
