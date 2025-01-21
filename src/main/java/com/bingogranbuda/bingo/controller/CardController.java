package com.bingogranbuda.bingo.controller;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/cards")
public class CardController {

    CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(){
        return ResponseEntity.ok(cardService.findAll());
    }

    @GetMapping("{id}")
    public Card getCardById(@PathVariable Integer id){
        return cardService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Card> createCard(@RequestBody Card card){

        Card createdCard = cardService.create(card);
        URI location = URI.create("/cards/" + createdCard.id());

        return ResponseEntity.created(location).body(card);
    }

    @DeleteMapping("{id}")
    public void deleteCard(@PathVariable Integer id){
        cardService.delete(id);
    }

    @PutMapping("{id}")
    public Card updateCard(@PathVariable Integer id, @RequestBody Card newCard){

        return cardService.update(id, newCard);
    }
}
