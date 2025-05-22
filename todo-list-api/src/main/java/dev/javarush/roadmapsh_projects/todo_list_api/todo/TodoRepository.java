package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import java.util.List;

public interface TodoRepository {
    Todo insertTodo (String username, String title, String description);

    List<Todo> findTodos(String username, Integer page, Integer size);
}
