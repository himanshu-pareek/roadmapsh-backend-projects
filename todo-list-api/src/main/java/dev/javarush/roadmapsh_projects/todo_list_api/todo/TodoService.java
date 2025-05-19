package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import org.springframework.stereotype.Service;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo (String username, String title, String description) {
        return repository.insertTodo(username, title, description);
    }
}
