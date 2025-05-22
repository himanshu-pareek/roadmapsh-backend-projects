package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import java.time.LocalDateTime;

public record Todo(
        long id,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
