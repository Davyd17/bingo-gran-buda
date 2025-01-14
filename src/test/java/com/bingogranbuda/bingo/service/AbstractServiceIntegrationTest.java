package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.exception.NotFoundException;
import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.Dao;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Testcontainers
public class AbstractServiceIntegrationTest {

    @Autowired
    AbstractService<User> abstractService;

    @Autowired
    Dao<User> dao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    List<User> persistedUsers;

    @BeforeEach
    void setUp(){

        abstractService = new AbstractService<User>(dao) {
            @Override
            protected String getEntityTypeSimpleName() {
                return User.class.getSimpleName();
            }
        };

        jdbcTemplate.execute("DELETE FROM users WHERE id > 0;");
        jdbcTemplate.execute("ALTER SEQUENCE users_id_seq RESTART WITH 1");

        //Arrange
        dao.insert(new User(1,
                "userTest1",
                "testPass1",
                new Timestamp(System.currentTimeMillis())));

        dao.insert(new User(2,
                "userTest2",
                "testPass2",
                new Timestamp(System.currentTimeMillis())));
    }

    @Test
    void findById_ReturnUser_WhenIdExist(){

        //Act
        User foundUser = abstractService.findById(2);

        //Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.username()).isEqualTo("userTest2");
    }

    @Test
    void findById_ThrowsNotFoundException_WhenIdNotFound(){

        //Act && Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            abstractService.findById(3);
        });

        assertThat(exception.getMessage())
                .isEqualTo("User with ID 3 not found, please enter a valid ID");
    }

    @Test
    void create_ShouldCreateUser_WhenInsertionIsCorrect() {

        //Arrange
        User newUSer = new User(null,
                "newUserTest",
                "newPassTest",
                new Timestamp(System.currentTimeMillis()));

        //Act
        abstractService.create(newUSer);

        Optional<User> userCreated = dao.getById(3);
        persistedUsers = dao.getAll();

        //Assert
        assertThat(userCreated).isPresent();
        assertThat(persistedUsers).hasSize(3);
    }

    @Test
    void delete_ShouldDeleteUser_WhenIdIsValid(){

        //Act
        abstractService.delete(2);

        persistedUsers = dao.getAll();

        //Assert
        assertThat(persistedUsers).hasSize(1);
    }

    @Test
    void update_ShouldUpdateUser_WhenIdAndUserAreValid(){

        //Arrange
        User newUser = new User(null,
                "newUserTest",
                "newPassTest",
                new Timestamp(System.currentTimeMillis()));

        //Act & Assert
        assertDoesNotThrow(() -> {
            abstractService.update(2, newUser);
        });

        persistedUsers = dao.getAll();
        Optional<User> userUpdated = dao.getById(2);

        assertThat(persistedUsers).hasSize(2);
        assertThat(userUpdated).isPresent();
        assertEquals(newUser.username(), userUpdated.get().username());
    }


}
