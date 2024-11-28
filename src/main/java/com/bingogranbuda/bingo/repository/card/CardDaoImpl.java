package com.bingogranbuda.bingo.repository.card;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.util.repository.UtilDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.util.List;
import java.util.Optional;

@Repository
public class CardDaoImpl implements CardDao{

    private final JdbcTemplate jdbcTemplate;

    public CardDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Card> getAll() {

        String sql = """
                SELECT id, numbers, status, user_id, game_id, selected_numbers, created_at
                FROM cards
                """;

        return jdbcTemplate.query(sql, new CardRowMapper());
    }

    @Override
    public Optional<Card> getById(Integer id) {

        String sql = """
                SELECT id, numbers, status, user_id, game_id, selected_numbers, created_at
                FROM cards
                WHERE id = ?;
                """;

        return jdbcTemplate.query(sql, new CardRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public int insert(Card entity) {

        String sql = """
                INSERT INTO cards (numbers, status, user_id, game_id, selected_numbers, created_at)
                VALUES (?, ?::card_status, ?, ?, ?, ?);
                """;

        Array numbers = UtilDao.listToSqlArray(jdbcTemplate, entity.numbers());
        Array selectedNumbers = UtilDao.listToSqlArray(jdbcTemplate, entity.selectedNumbers());

        return jdbcTemplate.update(sql, numbers, entity.status().name().toLowerCase(),
                entity.userId(), entity.gameId(), selectedNumbers, entity.created_at());
    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM cards
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int update(Integer id, Card entity) {

        String sql = """
                UPDATE cards
                SET numbers = ?, status = ?::card_status, game_id = ?, selected_numbers = ?, created_at = ?
                WHERE id = ?;
                """;

        Array numbers = UtilDao.listToSqlArray(jdbcTemplate, entity.numbers());
        Array selectedNumbers = UtilDao.listToSqlArray(jdbcTemplate, entity.selectedNumbers());

        return jdbcTemplate.update(sql, numbers, entity.status().name().toLowerCase(),
                entity.gameId(), selectedNumbers, entity.created_at(), id);
    }
}
