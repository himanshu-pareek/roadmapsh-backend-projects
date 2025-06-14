package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import java.time.LocalDateTime;

public record Todo(
        long id,
        String owner,
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
        Todo withTitleAndDescription(String titleNew, String descriptionNew) {
                return new Todo (id, owner, titleNew, descriptionNew, createdAt, LocalDateTime.now());
        }

        public Todo withId(long newId) {
                return new Todo (newId, owner, title, description, createdAt, updatedAt);
        }

        public Todo {
                // Validations go here
        }
}
