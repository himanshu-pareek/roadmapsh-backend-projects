package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.web;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;

import java.util.List;

public record TodoListResponse(
        Integer page,
        Integer size,
        List<TodoListItemResponse> data
) {
}
