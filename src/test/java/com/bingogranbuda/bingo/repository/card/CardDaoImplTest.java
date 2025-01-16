package com.bingogranbuda.bingo.repository.card;


import com.bingogranbuda.bingo.model.Card;
import com.bingogranbuda.bingo.model.status.CardStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class CardDaoImplTest {

    @Autowired
    CardDao cardDao;

    @Autowired
    JdbcTemplate jdbcTemplate;


    private final List<Integer> numbersFrom1to75 = new ArrayList<>(IntStream.rangeClosed(1,75)
            .map(i -> i + 1).boxed().toList());

    private List<Integer> pickCardNumbers(){

        Collections.shuffle(numbersFrom1to75);

        return numbersFrom1to75.stream().limit(24).toList();
    }

    private List<Integer> selectedNumbersFromCard(){

        Collections.shuffle(new ArrayList<>(pickCardNumbers()));

        return pickCardNumbers().stream().limit(5).toList();
    }


    Card newCard = new Card(
            null,
            pickCardNumbers(),
            CardStatus.UNDEFINED,
            2,
            1,
            selectedNumbersFromCard(),
            LocalDateTime.now()

    );

    @BeforeEach
    void setUp(){

        jdbcTemplate.execute("DELETE FROM cards WHERE id > 0;");
        jdbcTemplate.execute("ALTER SEQUENCE cards_id_seq RESTART WITH 1");
    }

    @Test
    void getAll(){

        cardDao.insert(new Card(
                null,
                pickCardNumbers(),
                CardStatus.UNDEFINED,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()

        ));

        cardDao.insert(new Card(
                null,
                pickCardNumbers(),
                CardStatus.WIN,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()

        ));


        List<Card> cards = cardDao.getAll();

        assertThat(cards).isNotEmpty();
        assertThat(cards.size()).isGreaterThan(1);
    }

    @Test
    void getById() {

        cardDao.insert(new Card(
                null,
                pickCardNumbers(),
                CardStatus.UNDEFINED,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()

        ));

        Optional<Card> optionalCard = cardDao.getById(1);

        assertThat(optionalCard.isPresent()).isEqualTo(true);
    }

    @Test
    void insert(){

        Card newCard = new Card(
                null,
                pickCardNumbers(),
                CardStatus.UNDEFINED,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()

        );

        int operationResult = cardDao.insert(newCard);

        assertThat(operationResult).isEqualTo(1);
    }

    @Test
    void delete(){

        cardDao.insert(new Card(
                null,
                pickCardNumbers(),
                CardStatus.UNDEFINED,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()
        ));

        int operationResult = cardDao.delete(1);

        assertThat(operationResult).isEqualTo(1);
    }

    @Test
    void update(){

        cardDao.insert(new Card(
                null,
                pickCardNumbers(),
                CardStatus.UNDEFINED,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()

        ));

        Card newCard = new Card(
                null,
                pickCardNumbers(),
                CardStatus.WIN,
                2,
                1,
                selectedNumbersFromCard(),
                LocalDateTime.now()
        );

        int result = cardDao.update(1, newCard);

        assertThat(result).isEqualTo(1);
    }


}
