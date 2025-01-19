package com.bingogranbuda.bingo.repository.game;

import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.model.Game;
import com.bingogranbuda.bingo.model.status.CardStatus;
import com.bingogranbuda.bingo.model.status.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class GameDaoImplTest {

    @Autowired
    GameDaoImpl gameDao;

    @Autowired
    JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("DELETE FROM games WHERE id > 0;");
        jdbcTemplate.execute("ALTER SEQUENCE games_id_seq RESTART WITH 1");
    }

    @Test
    void getAll(){

        gameDao.insert(new Game(
                null,
                GameStatus.WAITING,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        ));

        gameDao.insert(new Game(
                null,
                GameStatus.COMPLETED,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        ));

        List<Game> cards = gameDao.getAll();

        assertThat(cards).isNotEmpty();
        assertThat(cards.size()).isGreaterThan(1);
    }

    @Test
    void getById(){

        gameDao.insert(new Game(
                null,
                GameStatus.COMPLETED,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        ));

        Optional<Game> optionalCard = gameDao.getById(1);
        assertThat(optionalCard.isPresent()).isEqualTo(true);
    }

    @Test
    void insert(){

        Game newGame = new Game(
                null,
                GameStatus.COMPLETED,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        );

        assertThat(gameDao.insert(newGame)).isPresent();
    }

    @Test
    void delete(){

        gameDao.insert(new Game(
                null,
                GameStatus.COMPLETED,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        ));

        int result = gameDao.delete(1);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void update(){

        gameDao.insert(new Game(
                null,
                GameStatus.COMPLETED,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        ));

        Game newGame = new Game(
                null,
                GameStatus.WAITING,
                IntStream.range(1, 75)
                        .map(i -> i + 1)
                        .boxed().toList()
        );

        assertThat(gameDao.update(1, newGame)).isPresent();
    }
}
