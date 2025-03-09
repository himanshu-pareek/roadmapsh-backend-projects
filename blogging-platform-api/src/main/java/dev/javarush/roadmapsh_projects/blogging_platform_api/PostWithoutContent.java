package dev.javarush.roadmapsh_projects.blogging_platform_api;

import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.PostCategory;

import java.time.LocalDateTime;
import java.util.Collection;

public record PostWithoutContent (
        Integer id,
        String title,
        Collection<String> tags,
        PostCategory category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
