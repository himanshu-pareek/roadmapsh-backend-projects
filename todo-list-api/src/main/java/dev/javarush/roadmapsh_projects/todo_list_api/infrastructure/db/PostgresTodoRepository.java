package dev.javarush.roadmapsh_projects.todo_list_api.infrastructure.db;

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
import java.util.Optional;

@Repository
public class PostgresTodoRepository implements TodoRepository {

    private static final String INSERT_TODO_SQL = """
            insert into todos (title, description, owner, created_at, updated_at)
            values (:title, :description, :owner, :created_at, :updated_at)
            """;
    private static final String FIND_TODOS_SQL = """
            select id, title, description, created_at, updated_at
            from todos
            where owner = :owner
            order by updated_at desc
            offset :skip
            limit :limit
            """;
    private static final String FIND_TODO_USING_ID_SQL = """
            select id, title, owner, description, created_at, updated_at
            from todos
            where id = :id
            limit 1
            """;
    private static final String UPDATE_TODO_SQL = """
            update todos
            set title = :title,
                description = :description,
                updated_at = :updated_at
            where id = :id
            """;

    private static final String DELETE_TODO_SQL = """
            delete from todos where id = :id
            """;

    private final JdbcClient jdbc;

    public PostgresTodoRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Todo insertTodo(Todo todo) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsUpdated = jdbc.sql(INSERT_TODO_SQL)
                .param("title", todo.title())
                .param("description", todo.description())
                .param("owner", todo.owner())
                .param("created_at", todo.createdAt())
                .param("updated_at", todo.updatedAt())
                .update(keyHolder, "id");
        if (rowsUpdated != 1) {
            throw new RuntimeException("Something went wrong.");
        }
        if (keyHolder.getKey() == null) {
            throw new RuntimeException("Something went wrong.");
        }
        long id = keyHolder.getKey().longValue();
        return todo.withId(id);
    }

    @Override
    public List<Todo> findTodos(String owner, Integer page, Integer size) {
        return jdbc.sql(FIND_TODOS_SQL)
                .param("owner", owner)
                .param("skip", (page - 1) * size)
                .param("limit", size)
                .query(new RowMapper<Todo>() {
                    @Override
                    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return new Todo(
                                rs.getLong("id"),
                                owner,
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getTimestamp("created_at").toLocalDateTime(),
                                rs.getTimestamp("updated_at").toLocalDateTime());
                    }
                }).list();
    }

    @Override
    public Optional<Todo> findById(long id) {
        return this.jdbc.sql(FIND_TODO_USING_ID_SQL)
                .param("id", id)
                .query((rs, rowNum) -> new Todo(
                        rs.getLong("id"),
                        rs.getString("owner"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getTimestamp("updated_at").toLocalDateTime()))
                .optional();
    }

    @Override
    public void updateTodo(Todo todo) {
        this.jdbc.sql(UPDATE_TODO_SQL)
                .param("id", todo.id())
                .param("title", todo.title())
                .param("description", todo.description())
                .param("updated_at", todo.updatedAt())
                .update();
    }

    @Override
    public void deleteById(long id) {
        this.jdbc.sql(DELETE_TODO_SQL)
                .param("id", id)
                .update();
    }
}
