package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security.web;

import java.time.LocalDateTime;

public record LoginResponse(String token, LocalDateTime expiry, String type) {
}
