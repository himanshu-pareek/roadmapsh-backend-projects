package dev.javarush.roadmapsh_projects.todo_list_api.todo.web;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;
import dev.javarush.roadmapsh_projects.todo_list_api.todo.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo createTodo(@RequestBody @Validated TodoCreateRequest data) {
        String owner = loggedInUser();
        return service.createTodo(owner, data.title(), data.description());
    }

    @GetMapping
    public TodoList getTodos(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size", defaultValue = "10") Integer size) {
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        size = Math.min (size, 10);
        page = Math.max (1, page);
        size = Math.max (1, size);
        String username = loggedInUser();
        List<Todo> todos = service.getTodos(username, page, size);
        return new TodoList(page, todos.size(), todos);
    }

    private String loggedInUser() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
