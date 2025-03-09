package dev.javarush.roadmapsh_projects.blogging_platform_api.blog.dto;

import dev.javarush.roadmapsh_projects.blogging_platform_api.PostWithoutContent;
import dev.javarush.roadmapsh_projects.blogging_platform_api.blog.Post;

import java.util.Collection;

public record PostList(
        Collection<PostWithoutContent> posts
) {
}
