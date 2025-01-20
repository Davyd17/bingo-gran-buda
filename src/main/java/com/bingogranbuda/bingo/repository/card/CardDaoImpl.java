package com.bingogranbuda.bingo.repository.card;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.util.repository.sql_parameter.CardSQLParamBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CardDaoImpl implements CardDao {

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
    public Optional<Card> insert(Card entity) {

        CardSQLParamBuilder paramBuilder
                = new CardSQLParamBuilder(jdbcTemplate, entity);

        String sql = """
                INSERT INTO cards (numbers, status, user_id, game_id, selected_numbers, created_at)
                VALUES (?, ?::card_status, ?, ?, ?, ?)
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                new CardRowMapper(),
                paramBuilder.buildInsertParams())
                .stream()
                .findFirst();
    }

    @Override
    public Optional<Card> update(Integer id, Card entity) {

        CardSQLParamBuilder sqlParamBuilder
                = new CardSQLParamBuilder(jdbcTemplate, entity);

        String sql = """
                UPDATE cards
                SET numbers = ?, status = ?::card_status, user_id = ?, game_id = ?, selected_numbers = ?
                WHERE id = ?
                RETURNING *;
                """;

        return jdbcTemplate.query(sql,
                        new CardRowMapper(),
                        sqlParamBuilder.buildUpdateParams(id))
                .stream()
                .findFirst();
    }

    @Override
    public int delete(Integer id) {

        String sql = """
                DELETE FROM cards
                WHERE id = ?;
                """;

        return jdbcTemplate.update(sql, id);
    }
}
