package com.bingogranbuda.bingo.model;

import com.bingogranbuda.bingo.model.status.CardStatus;

import java.sql.Timestamp;
import java.util.List;

public record Card(
        Integer id,
        List<Integer> numbers,
        CardStatus status,
        Integer userId,
        Integer gameId,
        List<Integer> selectedNumbers,
        Timestamp created_at
) {
}
