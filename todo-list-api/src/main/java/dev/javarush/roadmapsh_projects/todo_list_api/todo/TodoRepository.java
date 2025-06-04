package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import java.util.List;

public interface TodoRepository {
    Todo insertTodo (String owner, String title, String description);

    List<Todo> findTodos(String owner, Integer page, Integer size);
}
