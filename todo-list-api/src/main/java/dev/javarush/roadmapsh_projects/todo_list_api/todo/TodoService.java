package dev.javarush.roadmapsh_projects.todo_list_api.todo;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.exceptions.TodoNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo createTodo (TodoCreateParameters parameters) {
        LocalDateTime now = LocalDateTime.now();
        Todo todo = new Todo(0, parameters.owner(), parameters.title(), parameters.description(), now, now);
        return repository.insertTodo(todo);
    }

    public List<Todo> getTodos(String owner, Integer page, Integer size) {
        return repository.findTodos(owner, page, size);
    }

    public Todo updateTodo(TodoUpdateParameters parameters) {
        Todo todo = this.repository.findById(parameters.id())
            .orElseThrow(() -> new TodoNotFoundException(parameters.id()));
        if (!Objects.equals(todo.owner(), parameters.owner())) {
            throw new AccessDeniedException("Todo " + parameters.id() + " is not accessible to " + parameters.owner());
        }
        Todo updatedTodo = todo.withTitleAndDescription(parameters.title(), parameters.description());
        this.repository.updateTodo(updatedTodo);
        return updatedTodo;
    }

    public void deleteTodo(TodoDeleteParameters parameters) {
        Todo todo = this.repository.findById(parameters.id())
                .orElseThrow(() -> new TodoNotFoundException(parameters.id()));
        if (!Objects.equals(todo.owner(), parameters.owner())) {
            throw new TodoNotFoundException(parameters.id());
        }
        this.repository.deleteById(parameters.id());
    }
}
