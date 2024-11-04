package com.bingogranbuda.bingo.repository.user;

import com.bingogranbuda.bingo.BingoProjectApplication;
import com.bingogranbuda.bingo.config.DataSourceConfigTest;
import com.bingogranbuda.bingo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest(classes = BingoProjectApplication.class) // Specify the main application class
@Import(DataSourceConfigTest.class) // Import your test configuration
@Transactional // Optionally for transaction management
@Rollback
public class DaoImplTest {

    @Autowired
    UserDao userDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp(){
        //jdbcTemplate.execute("DELETE FROM users WHERE id BETWEEN 1 AND 80");
    }

    @Test
    void testGetAll(){
        List<User> users = userDao.getAll();
        assertThat(users).isNotEmpty();
    }

    @Test
    void testGetById(){

        Optional<User> userOptional = userDao.getById(2);
        assertThat(userOptional.isPresent()).isTrue();
    }

    @Test
    void insertTest(){

        User newUser = new User(null,
                "userTest",
                "passwordTest",
                new Timestamp(System.currentTimeMillis()));

        int result = userDao.insert(newUser);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void deleteTest(){

        int result = userDao.delete(2);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void updateTest(){

        User newUser = new User(null,
                "userTest",
                "passwordTest",
                new Timestamp(System.currentTimeMillis()));

        int result = userDao.update(1, newUser);
        assertThat(result).isEqualTo(1);
    }


}
