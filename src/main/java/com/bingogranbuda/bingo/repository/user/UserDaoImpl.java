package com.bingogranbuda.bingo.repository.user;

import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.util.repository.sql_parameter.UserSQLParamBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

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

    public Optional<User> getByUsername(String username) {

        String sql = """
                SELECT id, username, password, created_at
                FROM users
                WHERE username = ?;
                """;

        return jdbcTemplate.query(sql, new UserRowMapper(), username)
                .stream().findFirst();
    }

    @Override
    public Optional<User> insert(User entity) {

        UserSQLParamBuilder paramBuilder
                = new UserSQLParamBuilder(entity);

        String sql = """
                INSERT INTO users (username, password, created_at)
                VALUES (?, ?, ?)
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                        new UserRowMapper(),
                        paramBuilder.buildInsertParams())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<User> update(Integer id, User entity) {

        UserSQLParamBuilder paramBuilder
                = new UserSQLParamBuilder(entity);

        String sql = """
                UPDATE users
                SET username = ?, password = ?, created_at = ?
                WHERE id = ?
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                        new UserRowMapper(),
                        paramBuilder.buildUpdateParams(id))
                .stream()
                .findFirst();

    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM users
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }
}
