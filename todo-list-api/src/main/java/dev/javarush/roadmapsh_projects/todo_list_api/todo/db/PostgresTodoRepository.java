package dev.javarush.roadmapsh_projects.todo_list_api.todo.db;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;
import dev.javarush.roadmapsh_projects.todo_list_api.todo.TodoRepository;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class PostgresTodoRepository implements TodoRepository {

    private static final String INSERT_TODO_SQL = """
    insert into todos (title, description, owner, completed, created_at, updated_at)
    values (:title, :description, :owner, :completed, :created_at, :updated_at)
    """;

    private final JdbcClient jdbc;

    public PostgresTodoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Todo insertTodo(String username, String title, String description) {
        LocalDateTime now = LocalDateTime.now();
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsUpdated = jdbc.sql(INSERT_TODO_SQL)
                .param("title", title)
                .param("description", description)
                .param("owner", username)
                .param("completed", false)
                .param("created_at", now)
                .param("updated_at", now)
                .update(keyHolder, "id");
        if (rowsUpdated != 1) {
            throw new RuntimeException("Something went wrong.");
        }
        if (keyHolder.getKey() == null) {
            throw new RuntimeException("Something went wrong.");
        }
        long id = keyHolder.getKey().longValue();
        return new Todo(id, title, description, false, now, now);
    }
}
