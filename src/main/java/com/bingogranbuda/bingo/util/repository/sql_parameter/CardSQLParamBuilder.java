package com.bingogranbuda.bingo.util.repository.sql_parameter;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.util.repository.SqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;
import java.time.LocalDateTime;

public class CardSQLParamBuilder extends SQLParamBuilder<Card> {


    public CardSQLParamBuilder(JdbcTemplate jdbcTemplate, Card entity) {
        super(jdbcTemplate, entity);
    }

    private final Array numbers = SqlUtil.listToSqlArray(jdbcTemplate, entity.numbers());
    private final Array selectedNumbers = SqlUtil.listToSqlArray(jdbcTemplate, entity.selectedNumbers());

    @Override
    public Object[] buildInsertParams() {

        return new Object[]{
                numbers,
                entity.status().name().toLowerCase(),
                entity.userId(),
                entity.gameId(),
                selectedNumbers,
                LocalDateTime.now()
        };
    }

    @Override
    public Object[] buildUpdateParams(Integer id) {

        return new Object[]{
                numbers,
                entity.status().name().toLowerCase(),
                entity.userId(),
                entity.gameId(),
                selectedNumbers,
                entity.created_at(),
                id
        };
    }
}
