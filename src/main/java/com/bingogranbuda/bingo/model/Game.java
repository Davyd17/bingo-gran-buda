package com.bingogranbuda.bingo.model;

import com.bingogranbuda.bingo.model.status.GameStatus;

import java.util.List;

public record Game(
        Integer id,
        GameStatus status,
        List<Integer> generatedBallots
) {
}
