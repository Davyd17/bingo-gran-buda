package com.bingogranbuda.bingo.model;

import java.time.LocalDateTime;

public record User(
        Integer id,
        String username,
        String password,
        LocalDateTime createdAt
) {
}
