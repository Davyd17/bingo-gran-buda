package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.exception.DuplicateRecordException;
import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.user.UserDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Timestamp;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Testcontainers
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<User> persistedUsers;

    @BeforeEach
    void setUp() {

        jdbcTemplate.execute("DELETE FROM users WHERE id > 0;");
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");

        //Arrange
        userDao.insert(new User(1,
                "userTest1",
                "testPass1",
                new Timestamp(System.currentTimeMillis())));

        userDao.insert(new User(2,
                "userTest2",
                "testPass2",
                new Timestamp(System.currentTimeMillis())));
    }

    @Test
    void create_ShouldThrowDuplicateRecordException_WhenInsertExistentUsername(){

        String duplicateUsername = "userTest1";

        //Arrange
        User wrongUser = new User(1,
                duplicateUsername,
                "testPass1",
                new Timestamp(System.currentTimeMillis()));

        //Act & Assert
        assertThrows(DuplicateRecordException.class, () -> {
            userService.create(wrongUser);
        });

        persistedUsers = userDao.getAll();

        //Assert that user wasn't created
        assertThat(persistedUsers).hasSize(2);
    }
}
