package dev.javarush.roadmapsh_projects.blogging_platform_api.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
