package dev.javarush.roadmapsh_projects.todo_list_api.todo;

public record TodoUpdateParameters(long id, String title, String description, String owner) {
}
