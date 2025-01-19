package com.bingogranbuda.bingo.repository.game;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.util.repository.sql_parameter.GameSQLParamBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GameDaoImpl implements GameDao {

    private final JdbcTemplate jdbcTemplate;

    public GameDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> getAll() {

        String sql = """
                SELECT id, status, generated_ballots
                FROM games;
                """;

        return jdbcTemplate.query(sql, new GameRowMapper());
    }

    @Override
    public Optional<Game> getById(Integer id) {

        String sql = """
                SELECT id, status, generated_ballots
                FROM games
                WHERE id = ?
                """;

        return jdbcTemplate.query(sql, new GameRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Game> insert(Game entity) {

        GameSQLParamBuilder paramBuilder
                = new GameSQLParamBuilder(jdbcTemplate, entity);

        String sql = """
                INSERT INTO games (status, generated_ballots)
                VALUES(?::game_status, ?)
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                        new GameRowMapper(),
                        paramBuilder.buildInsertParams())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Game> update(Integer id, Game entity) {

        GameSQLParamBuilder paramBuilder
                = new GameSQLParamBuilder(jdbcTemplate, entity);

        String sql = """
                UPDATE games
                SET status = ?::game_status, generated_ballots = ?
                WHERE id = ?
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                        new GameRowMapper(),
                        paramBuilder.buildUpdateParams(id))
                .stream()
                .findFirst();
    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM games
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }
}
