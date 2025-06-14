package dev.javarush.roadmapsh_projects.todo_list_api.todo.exceptions;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(long id) {
        super("Todo not found: " + id);
    }

}
