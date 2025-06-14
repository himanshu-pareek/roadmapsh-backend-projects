package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Todo insertTodo (Todo todo);

    List<Todo> findTodos(String owner, Integer page, Integer size);

    Optional<Todo> findById(long id);

    void updateTodo(Todo updatedTodo);

    void deleteById(long id);
}
