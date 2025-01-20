package com.bingogranbuda.bingo.util.repository.sql_parameter;

import com.bingogranbuda.bingo.model.User;

import java.time.LocalDateTime;

public class UserSQLParamBuilder extends SQLParamBuilder<User>{


    public UserSQLParamBuilder(User entity) {
        super(entity);
    }

    @Override
    public Object[] buildInsertParams() {
        return new Object[]{
                entity.username(),
                entity.password(),
                LocalDateTime.now()
        };
    }

    @Override
    public Object[] buildUpdateParams(Integer id) {
        return new Object[]{
                entity.username(),
                entity.password(),
                id
        };
    }
}
