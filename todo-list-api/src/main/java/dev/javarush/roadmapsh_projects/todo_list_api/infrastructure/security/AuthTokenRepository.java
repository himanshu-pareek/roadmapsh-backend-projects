package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security;

import java.util.Optional;

public interface AuthTokenRepository {
    AuthToken createAndSaveToken(String username);

    Optional<String> findUsernameForToken(String token);
}
