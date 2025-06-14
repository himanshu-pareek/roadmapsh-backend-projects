package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.web;

import com.fasterxml.jackson.annotation.JsonAlias;
import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;

import java.time.LocalDateTime;

public record TodoListItemResponse(long id, String title, String description, @JsonAlias("updated_at") LocalDateTime updatedAt) {
    public static TodoListItemResponse fromTodo(Todo todo) {
        return new TodoListItemResponse(todo.id(), todo.title(), todo.description(), todo.updatedAt());
    }
}
