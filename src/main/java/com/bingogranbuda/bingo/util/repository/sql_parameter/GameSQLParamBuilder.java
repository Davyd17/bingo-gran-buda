package com.bingogranbuda.bingo.util.repository.sql_parameter;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.util.repository.SqlUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Array;

public class GameSQLParamBuilder extends SQLParamBuilder<Game>{

    public GameSQLParamBuilder(JdbcTemplate jdbcTemplate, Game entity) {
        super(jdbcTemplate, entity);
    }

    public GameSQLParamBuilder(Game entity) {
        super(entity);
    }

    private final Array generatedBallots = SqlUtil.listToSqlArray(jdbcTemplate,entity.generatedBallots());
    private final String status = entity.status().name().toLowerCase();

    @Override
    public Object[] buildInsertParams() {
        return new Object[]{
                status,
                generatedBallots
        };
    }

    @Override
    public Object[] buildUpdateParams(Integer id) {
        return new Object[]{
                status,
                generatedBallots,
                id
        };
    }
}
