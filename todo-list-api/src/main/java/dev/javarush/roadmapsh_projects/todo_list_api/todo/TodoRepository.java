package dev.javarush.roadmapsh_projects.todo_list_api.todo;

public interface TodoRepository {
    Todo insertTodo (String username, String title, String description);
}
