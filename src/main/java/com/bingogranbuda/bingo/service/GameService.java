package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.repository.game.GameDao;

public class GameService extends AbstractService<Game>{

    public GameService(GameDao gameDao) {
        super(gameDao);
    }

    @Override
    protected String getEntityTypeSimpleName() {
        return Game.class.getSimpleName();
    }
}
