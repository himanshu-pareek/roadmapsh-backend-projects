package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
