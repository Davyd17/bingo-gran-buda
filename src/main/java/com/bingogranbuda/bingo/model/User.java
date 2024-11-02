package com.bingogranbuda.bingo.model;

import java.sql.Timestamp;

public record User(
        Integer id,
        String username,
        String password,
        Timestamp createdAt
) {
}
