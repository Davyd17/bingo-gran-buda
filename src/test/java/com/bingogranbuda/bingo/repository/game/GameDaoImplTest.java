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

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Rollback
public class GameDaoImplTest {

    @Autowired
    GameDaoImpl gameDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    Game newGame = new Game(
            null,
            GameStatus.WAITING,
            List.of(IntStream.rangeClosed(1,75).iterator().next())
    );


    @BeforeEach
    void setUp(){

    }

    @Test
    void getAll(){

        List<Game> cards = gameDao.getAll();
        assertThat(cards).isNotEmpty();
    }

    @Test
    void getById(){

        Optional<Game> optionalCard = gameDao.getById(2);
        assertThat(optionalCard.isPresent()).isEqualTo(true);
    }

    @Test
    void insert(){

        int result = gameDao.insert(newGame);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void delete(){

        int result = gameDao.delete(2);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void update(){

        int result = gameDao.update(1, newGame);
        assertThat(result).isEqualTo(1);
    }

}
