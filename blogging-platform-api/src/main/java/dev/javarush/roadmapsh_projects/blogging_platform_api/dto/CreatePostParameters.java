package dev.javarush.roadmapsh_projects.blogging_platform_api.dto;

import dev.javarush.roadmapsh_projects.blogging_platform_api.PostCategory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreatePostParameters(
        @NotNull @Length(min = 10, max = 100) String title,
        @NotNull @Length(min = 10, max = 10000) String content,
        @NotNull PostCategory category,
        @NotNull @Size(min = 1, max = 5) List<String> tags
) {
}
