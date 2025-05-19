package dev.javarush.roadmapsh_projects.todo_list_api.todo.web;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;
import dev.javarush.roadmapsh_projects.todo_list_api.todo.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        String username = loggedInUser();
        return service.createTodo(username, data.title(), data.description());
    }

    private String loggedInUser() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
