package com.bingogranbuda.bingo.repository.card;


import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.model.status.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Rollback
public class CardDaoImplTest {

    @Autowired
    CardDao cardDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private int[] generateRandomNumbers(){

        int randomNumberFrom1To76 = (int) ((Math.random() * 75) + 1);

        return IntStream.rangeClosed(0,24).map(i -> randomNumberFrom1To76).toArray();
    }

    private final int[] cardNumbers = generateRandomNumbers();

    private final int[] selectedRandomNumbers = IntStream.rangeClosed(1, 5)
                                            .map(i  -> cardNumbers[(int)(Math.random() * 4)])
                                            .toArray();

    Card newCard = new Card(
            null,
            List.of(Arrays.stream(generateRandomNumbers()).iterator().next()),
            CardStatus.UNDEFINED,
            2,
            1,
            List.of(Arrays.stream(selectedRandomNumbers).iterator().next()),
            new Timestamp(System.currentTimeMillis())

    );


    @BeforeEach
    void setUp(){

    }

    @Test
    void getAll(){

        List<Card> cards = cardDao.getAll();
        assertThat(cards).isNotEmpty();
    }

    @Test
    void getById(){

        Optional<Card> optionalCard = cardDao.getById(2);
        assertThat(optionalCard.isPresent()).isEqualTo(true);
    }

    @Test
    void insert(){

        int result = cardDao.insert(newCard);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void delete(){

        int result = cardDao.delete(2);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void update(){

        int result = cardDao.update(1, newCard);
        assertThat(result).isEqualTo(1);
    }


}
