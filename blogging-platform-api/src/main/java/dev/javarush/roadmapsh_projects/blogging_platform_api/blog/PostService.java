package dev.javarush.roadmapsh_projects.blogging_platform_api.blog;

import dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto.CreatePostParameters;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto.UpdatePostParameters;
import dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions.BadRequestException;
import dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    public PostService(PostRepository postRepository, PostCategoryRepository postCategoryRepository) {
        this.postRepository = postRepository;
        this.postCategoryRepository = postCategoryRepository;
    }

    private PostCategory getPostCategory(Integer id) {
        return this.postCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post category with id " + id + " not found"));
    }

    public Post createPost(CreatePostParameters parameters) {
        // First, see if the post category is present or not in the database
        PostCategory category = getPostCategory(parameters.categoryId());
        Post post = new Post(
                parameters.title(),
                parameters.content(),
                category,
                parameters.tags()
        );
        return postRepository.save(post);
    }

    public Post getPost(Integer id) {
        return this.postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));
    }

    public Collection<PostWithoutContent> getAllPosts() {
        return this.postRepository.findAllWithoutContent();
    }

    public Post updatePost(Integer id, UpdatePostParameters parameters) {
        Post post = getPost(id);
        parameters.update(post, this::getPostCategory);
        return this.postRepository.save(post);
    }

    public void deletePost(Integer id) {
        Post post = getPost(id);
        this.postRepository.delete(post);
    }
}
