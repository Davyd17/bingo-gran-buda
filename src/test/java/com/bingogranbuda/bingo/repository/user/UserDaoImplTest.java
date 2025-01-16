package com.bingogranbuda.bingo.repository.user;

import com.bingogranbuda.bingo.model.User;
import org.junit.jupiter.api.AfterEach;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserDaoImplTest {

    @Autowired
    UserDao userDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        jdbcTemplate.execute("DELETE FROM users WHERE id > 0;");
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");
    }

    @AfterEach
    void tearDown(){
    }

    @Test
    void testGetAll(){

        userDao.insert(new User (
                null,
                "userTest",
                "passwordTest",
                LocalDateTime.now()
        ));

        userDao.insert(new User (
                null,
                "userTest2",
                "passwordTest",
                LocalDateTime.now()
        ));

        List<User> users = userDao.getAll();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void testGetById(){

        userDao.insert(new User (
                null,
                "userTest",
                "passwordTest",
                LocalDateTime.now()
        ));

        Optional<User> userOptional = userDao.getById(1);

        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    void testGetByUsername(){

        userDao.insert(new User (
                null,
                "userTest",
                "passwordTest",
                LocalDateTime.now()
        ));

        Optional<User> userOptional = userDao.getByUsername("userTest");

        assertThat(userOptional).isPresent();
    }

    @Test
    void insertTest(){

        User newUser = new User(null,
                "userTest",
                "passwordTest",
                LocalDateTime.now());

        int result = userDao.insert(newUser);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void deleteTest(){

        userDao.insert(new User (
                null,
                "userTest",
                "passwordTest",
                LocalDateTime.now()
        ));

        int result = userDao.delete(1);

        assertThat(result).isEqualTo(1);
    }

    @Test
    void updateTest(){

        userDao.insert(new User (
                null,
                "userTest",
                "passwordTest",
                LocalDateTime.now()
        ));

        User newUser = new User (
                null,
                "userUpdateTest",
                "myPasswordTest",
                LocalDateTime.now()
        );

        int result = userDao.update(1, newUser);

        assertThat(result).isEqualTo(1);
    }


}
