package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.repository.card.CardDao;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardServiceTest {

    @Mock
    CardDao cardDao;

    @InjectMocks
    CardService cardService;
}