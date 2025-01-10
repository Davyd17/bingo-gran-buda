package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.repository.card.CardDao;
import org.springframework.stereotype.Service;

@Service
public class CardService extends AbstractService<Card>{

    public CardService(CardDao cardDao) {
        super(cardDao);
    }

    @Override
    protected String getEntityTypeSimpleName() {
        return Card.class.getSimpleName();
    }
}
