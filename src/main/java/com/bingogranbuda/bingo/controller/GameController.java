package com.bingogranbuda.bingo.controller;

import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/games")
public class GameController {

    GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public List<Game> getAllGames(){
        return gameService.findAll();
    }

    @GetMapping("{id}")
    public Game getGameById(@PathVariable Integer id){
        return gameService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@RequestBody Game game){

        Game createdGame = gameService.create(game);
        URI location = URI.create("/games/" + createdGame.id());

        return ResponseEntity.created(location).body(createdGame);
    }

    @DeleteMapping("{id}")
    public void deleteGame(@PathVariable Integer id){
        gameService.delete(id);
    }

    @PutMapping("{id}")
    public Game updateGame(@PathVariable Integer id, @RequestBody Game newGame){
        return gameService.update(id, newGame);
    }
}
