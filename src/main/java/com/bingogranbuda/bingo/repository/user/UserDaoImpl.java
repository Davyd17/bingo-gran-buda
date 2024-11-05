package com.bingogranbuda.bingo.repository.user;

import com.bingogranbuda.bingo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getAll() {

        String sql = """
                SELECT id, username, password, created_at
                FROM users
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public Optional<User> getById(Integer id) {

        String sql = """
                SELECT id, username, password, created_at
                FROM users
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new UserRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public int insert(User entity) {

        String sql = """
                INSERT INTO users (username, password, created_at)
                VALUES (?, ?, ?);
                """;

        return jdbcTemplate.update(sql, entity.username(), entity.password(), entity.createdAt());
    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM users
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Integer id, User entity) {

        String sql = """
                UPDATE users
                SET username = ?, password = ?
                WHERE id = ?;
                """;
        return jdbcTemplate.update(sql, entity.username(), entity.password(), id);
    }
}
