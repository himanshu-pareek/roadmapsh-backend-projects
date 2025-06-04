package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo (String owner, String title, String description) {
        return repository.insertTodo(owner, title, description);
    }

    public List<Todo> getTodos(String owner, Integer page, Integer size) {
        return repository.findTodos(owner, page, size);
    }
}
