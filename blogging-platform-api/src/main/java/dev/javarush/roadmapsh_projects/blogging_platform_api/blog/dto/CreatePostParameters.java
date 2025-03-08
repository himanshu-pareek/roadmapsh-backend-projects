package dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreatePostParameters(
        @NotNull(message = "{NotNull.title}") @Length(min = 10, max = 100, message = "{Length.title}") String title,
        @NotNull(message = "{NotNull.content}") @Length(min = 10, max = 10000, message = "{Length.content}") String content,
        @JsonAlias("category-id") @NotNull(message = "{NotNull.categoryId}") Integer categoryId,
        @NotNull(message = "{NotNull.tags}") @Size(min = 1, max = 5, message = "{Size.tags}") List<String> tags
) {
}
