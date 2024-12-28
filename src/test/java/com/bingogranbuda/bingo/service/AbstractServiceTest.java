package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.exception.NotFoundException;
import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.user.UserDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AbstractServiceTest {

    AbstractService<User> abstractService;

    @Mock
    UserDaoImpl userDao;


    @BeforeEach
    void setUp() {

        abstractService = new AbstractService<>(userDao) {
            @Override
            protected String getEntityTypeSimpleName() {
                return User.class.getSimpleName();
            }
        };
    }

    @Test
    void findAll_ReturnAllUsers() {

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        User user2 = new User(2,
                "user2Test",
                "pass2Test",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getAll()).thenReturn(List.of(user, user2));

        //Act
        List<User> users = abstractService.findAll();

        //Assert
        assertThat(users).isNotEmpty()
                .hasSize(2)
                .containsExactly(user, user2);
    }

    @Test
    void findById_ReturnUserById() {

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(1)).thenReturn(Optional.of(user));

        //Act
        User testUser = abstractService.findById(1);

        //Assert
        assertThat(testUser).isNotNull();
    }

    @Test
    void findById_ThrowNotFoundException_WhenNoUserFound() {

        //Arrange
        Optional<User> user = Optional.empty();
        when(userDao.getById(3)).thenReturn(user);

        //Act & Assert
        assertThrows(NotFoundException.class, () -> {
            abstractService.findById(3);
        });
    }

    @Test
    void create_CanCreateUser() {

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.insert(user)).thenReturn(1);

        //Act & Assert
        assertDoesNotThrow(() -> abstractService.create(user));
        verify(userDao, times(1)).insert(user);
    }

    @Test
    void create_ThrowIllegalStateException_WhenCreationFails() {

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.insert(user)).thenReturn(0);

        //Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            abstractService.create(user);
        });

        verify(userDao, times(1)).insert(user);
    }

    @Test
    void delete_CanDeleteRecord_WhenIdFound() {

        int validId= 2;

        //Arrange
        User user = new User(2,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(validId)).thenReturn(Optional.of(user));
        when(userDao.delete(validId)).thenReturn(1);

        //Act & Assert
        assertDoesNotThrow(() -> {
            abstractService.delete(validId);
        });

        verify(userDao, times(1)).getById(validId);
        verify(userDao, times(1)).delete(validId);
    }

    @Test
    void delete_ThrowNotFoundException_WhenIdDoesNotExist(){

        int invalidId = 3;

        //Arrange
        when(userDao.getById(invalidId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class, () -> {
            abstractService.delete(invalidId);
        });

        verify(userDao, times(1)).getById(invalidId);
        verify(userDao, never()).delete(anyInt());
    }

    @Test
    void delete_ThrowIllegalStateException_WhenDeletionFAils(){

        //Arrange
        User user = new User(2,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(2)).thenReturn(Optional.of(user));
        when(userDao.delete(2)).thenReturn(0);

        //Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            abstractService.delete(2);
        });

        verify(userDao, times(1)).getById(2);
        verify(userDao, times(1)).delete(2);
    }



    @Test
    void update_CanUpdate_WhenIdFound() {

        int validId = 1;

        //Arrange
        User user = new User(2,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        User newUser = new User(1,
                "newUserTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(validId)).thenReturn(Optional.of(user));
        when(userDao.update(validId, newUser)).thenReturn(1);

        //Act & Assert
        assertDoesNotThrow(() -> {
            abstractService.update(validId, newUser);
        });

        verify(userDao, times(1)).getById(validId);
        verify(userDao, times(1)).update(validId, newUser);
    }

    @Test
    void update_ThrowNotFoundException_WhenIdDoesNotExist(){

        int invalidId = 3;

        //Arrange
        User newUser = new User(null,
                "newUserTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(invalidId)).thenReturn(Optional.empty());

        //Act && Assert
        assertThrows(NotFoundException.class, () -> {
            abstractService.update(invalidId, newUser);
        });

        verify(userDao, times(1)).getById(invalidId);
        verify(userDao, never()).update(invalidId, newUser);
    }

    @Test
    void update_ThrowIllegalStateException_WhenUpdateFails(){

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        User newUser = new User(1,
                "newUserTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(1)).thenReturn(Optional.of(user));
        when(userDao.update(1, newUser)).thenReturn(0);

        //Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            abstractService.update(1, newUser);
        });
    }
}
