package dev.javarush.roadmapsh_projects.todo_list_api.todo.web;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;

import java.util.List;

public record TodoList(
        Integer page,
        Integer size,
        List<Todo> data
) {
}
