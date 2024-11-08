package com.bingogranbuda.bingo.repository.game;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.util.repository_util.UtilDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

@Repository
public class GameDaoImpl implements GameDao{

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
    public int insert(Game entity) {

        String sql = """
                INSERT INTO games (status, generated_ballots)
                VALUES(?::game_status, ?);
                """;

        Array generatedBallots = UtilDao.listToSqlArray(jdbcTemplate,entity.generatedBallots());
        String status = entity.status().name().toLowerCase();

        return jdbcTemplate.update(sql, status, generatedBallots);
    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM games
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Integer id, Game entity) {

        String sql = """
                UPDATE games
                SET status = ?::game_status, generated_ballots = ?
                WHERE id = ?;
                """;

        Array generatedBallots = UtilDao.listToSqlArray(jdbcTemplate,entity.generatedBallots());


        return jdbcTemplate.update(sql, entity.status().name().toLowerCase(), generatedBallots, id);
    }
}
