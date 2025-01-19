package com.bingogranbuda.bingo.util.repository.sql_parameter;

import org.springframework.jdbc.core.JdbcTemplate;

public abstract class SQLParamBuilder<T> {

    protected JdbcTemplate jdbcTemplate;
    protected final T entity;

    public SQLParamBuilder(JdbcTemplate jdbcTemplate, T entity) {
        this.jdbcTemplate = jdbcTemplate;
        this.entity = entity;
    }

    public SQLParamBuilder(T entity) {
        this.entity = entity;
    }

    public abstract Object[] buildInsertParams();

    public abstract Object[] buildUpdateParams(Integer id);
}
