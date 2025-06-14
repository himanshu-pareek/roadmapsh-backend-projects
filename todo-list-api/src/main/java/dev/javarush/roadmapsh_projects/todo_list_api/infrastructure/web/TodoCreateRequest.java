package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.web;

import org.springframework.util.Assert;

public record TodoCreateRequest(
        String title,
        String description
) {
    public TodoCreateRequest {
        Assert.notNull(title, "title is required to create a todo");
        if (description == null) {
            description = "";
        }

        title = title.strip();
        description = description.strip();

        Assert.isTrue(title.length() <= 100, "title must not have length more than 100");
        Assert.isTrue(description.length() <= 1000, "description must not have length more than 1000");
    }
}
