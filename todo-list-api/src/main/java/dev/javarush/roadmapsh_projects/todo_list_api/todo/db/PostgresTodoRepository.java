package dev.javarush.roadmapsh_projects.todo_list_api.todo.db;

import dev.javarush.roadmapsh_projects.todo_list_api.todo.Todo;
import dev.javarush.roadmapsh_projects.todo_list_api.todo.TodoRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostgresTodoRepository implements TodoRepository {

    private static final String INSERT_TODO_SQL = """
    insert into todos (title, description, owner, completed, created_at, updated_at)
    values (:title, :description, :owner, :completed, :created_at, :updated_at)
    """;
    private static final String FIND_TODOS_SQL = """
    select id, title, description, completed, created_at, updated_at
    from todos
    where owner = :owner
        and completed = false
    order by updated_at desc
    offset :skip
    limit :limit
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
        return new Todo(id, title, description, now, now);
    }

    @Override
    public List<Todo> findTodos(String username, Integer page, Integer size) {
        return jdbc.sql(FIND_TODOS_SQL)
                .param("owner", username)
                .param("skip", (page - 1) * size)
                .param("limit", size)
                .query(new RowMapper<Todo>() {
                    @Override
                    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Todo(
                                rs.getLong("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getTimestamp("created_at").toLocalDateTime(),
                                rs.getTimestamp("updated_at").toLocalDateTime()
                        );
                    }
                }).list();
    }
}
