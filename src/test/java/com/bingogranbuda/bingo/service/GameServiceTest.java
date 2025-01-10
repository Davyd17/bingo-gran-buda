package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.repository.game.GameDaoImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    GameDaoImpl gameDao;

    @InjectMocks
    GameService gameService;

}