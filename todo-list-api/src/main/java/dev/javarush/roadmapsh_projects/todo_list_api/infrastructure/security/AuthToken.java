package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public record AuthToken(String token, String type, @JsonIgnore LocalDateTime createdAt, @JsonAlias("expiry") LocalDateTime expiresAt) {
}
