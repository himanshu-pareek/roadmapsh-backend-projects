package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.security;

import dev.javarush.roadmapsh_projects.todo_list_api.auth.AuthToken;
import dev.javarush.roadmapsh_projects.todo_list_api.auth.AuthTokenRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SQLAuthTokenRepository implements AuthTokenRepository {
    private static final String DELETE_TOKEN_SQL = "delete from auth_tokens where username = :username";
    private static final String INSERT_TOKEN_SQL = "insert into auth_tokens (username, token, created_at, expires_at) values (:username, :token, :created_at, :expires_at)";
    private static final String AUTH_TOKEN_EXISTS_SQL = "select 1 from auth_tokens where token = :token";
    private static final String FIND_USERNAME_SQL = "select username from auth_tokens where token = :token and expires_at > :current_time";

    private static final int tokenTTLSeconds = 60 * 60;

    private final JdbcClient jdbc;

    public SQLAuthTokenRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    private void deleteTokenForUsername(String username) {
        jdbc.sql(DELETE_TOKEN_SQL).param("username", username).update();
    }

    @Override
    public AuthToken createAndSaveToken(String username) {
        this.deleteTokenForUsername(username);
        String token = UUID.randomUUID().toString();
        while (true) {
            Optional<Object> obj = jdbc.sql(AUTH_TOKEN_EXISTS_SQL).param("token", token).query().optionalValue();
            if (obj.isEmpty()) {
                break;
            }
            token = UUID.randomUUID().toString();
        }
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime expiresAt = createdAt.plusSeconds(tokenTTLSeconds);
        jdbc.sql(INSERT_TOKEN_SQL)
                .param("username", username)
                .param("token", token)
                .param("created_at", createdAt)
                .param("expires_at", expiresAt)
                .update();
        return new AuthToken(token, "bearer", createdAt, expiresAt);
    }

    @Override
    public Optional<String> findUsernameForToken(String token) {
        Optional<Object> username = jdbc.sql(FIND_USERNAME_SQL)
                .param("token", token)
                .param("current_time", LocalDateTime.now())
                .query()
                .optionalValue();
        return username.map(o -> (String) o);
    }
}
