package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security.web;

import org.springframework.util.Assert;

public record RegisterRequestPayload(String email, String password) {
    public RegisterRequestPayload {
        Assert.notNull(email, "'email' is required");
        Assert.notNull(password, "'password' is required");
        email = email.trim().toLowerCase();
        password = password.trim().toLowerCase();
        Assert.hasText(email, "'email' must not be blank");
        Assert.hasText(password, "'password' must not be blank");
        Assert.isTrue(email.length() <= 50, "'email' must not contain more than 50 characters");
        Assert.isTrue(password.length() <= 50, "'password' must not contain more than 50 characters");
    }
}
