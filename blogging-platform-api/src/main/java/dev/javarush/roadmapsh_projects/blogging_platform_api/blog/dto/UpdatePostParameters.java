package dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.Post;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.PostCategory;
import dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions.BadRequestException;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.function.Function;

public record UpdatePostParameters(
    @Length(min = 10, max = 100, message = "{Length.title}") String title,
    @Length(min = 10, max = 10000, message = "{Length.content}") String content,
    @JsonAlias("category-id") Integer categoryId,
    @Size(min = 1, max = 5, message = "{Size.tags}") List<String> tags
) {
    public UpdatePostParameters {
        if (title == null && content == null && categoryId == null && tags == null) {
            throw new BadRequestException("At least one of title, content, categoryId, tags must be provided");
        }
    }

    public void update(Post post, Function<Integer, PostCategory> categoryLookup) {
        if (title != null) {
            post.setTitle(title);
        }
        if (content != null) {
            post.setContent(content);
        }
        if (categoryId != null) {
            post.setCategory(categoryLookup.apply(categoryId));
        }
        if (tags != null) {
            post.setTags(tags);
        }
    }
}
