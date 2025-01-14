package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.exception.DuplicateRecordException;
import com.bingogranbuda.bingo.exception.NotFoundException;
import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.user.UserDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserDaoImpl userDao;

    @InjectMocks
    UserService userService;

    @Test
    void verifyUsernameDuplication_ThrowsException_WhenUserNameAlreadyExist(){

        String duplicateUsername = "userTest";

        //Arrange
        User user = new User(1,
                duplicateUsername,
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getByUsername(duplicateUsername)).thenReturn(Optional.of(user));

        //Act && Assert
        assertThrows(DuplicateRecordException.class, () -> {
            userService.create(user);
        });

        verify(userDao, times(1)).getByUsername(duplicateUsername);
        verify(userDao, never()).insert(any(User.class));
    }

    @Test
    void create_InsertsUser_WhenUserNameIsUnique() {

        //Arrange
        User user = new User(1,
                "userTest",
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getByUsername("userTest")).thenReturn(Optional.empty());
        when(userDao.insert(user)).thenReturn(1);

        //Act & Assert
        assertDoesNotThrow(() -> userService.create(user));
        verify(userDao, times(1)).getByUsername("userTest");
        verify(userDao, times(1)).insert(any(User.class));
    }

    @Test
    void create_ThrowsException_WhenInsertionFails(){

        String uniqueUsername = "newUser";

        //Arrange
        User user = new User(1,
                uniqueUsername,
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getByUsername(uniqueUsername)).thenReturn(Optional.empty());
        when(userDao.insert(user)).thenReturn(0);

        //Act && Arrange
        assertThrows(IllegalStateException.class, () -> {
            userService.create(user);
        });

        verify(userDao, times(1)).getByUsername(uniqueUsername);
        verify(userDao, times(1)).insert(any(User.class));
    }

    @Test
    void update_UpdateUserNameAndPassword_WhenUserNameIsUnique() {

        String uniqueUserName = "newUser";
        String oldUserName = "oldUser";

        //Arrange
        User newUser = new User(null,
                uniqueUserName,
                "newPassTest",
                new Timestamp(System.currentTimeMillis()));

        User oldUser = new User(1,
                oldUserName,
                "oldPassTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getByUsername(newUser.username())).thenReturn(Optional.empty());
        when(userDao.getById(1)).thenReturn(Optional.of(oldUser));
        when(userDao.update(1, newUser)).thenReturn(1);

        //Act & Assert
        assertDoesNotThrow(() -> {
            userService.update(1, newUser);
        });

        verify(userDao, times(1)).getByUsername(anyString());
        verify(userDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).update(anyInt(), any(User.class));
    }

    @Test
    void update_ThrowsException_WhenTheSearchedIdDoesNotExist(){

        //Arrange
        int idSearched = 2;

        when(userDao.getById(idSearched)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class, () -> {
            userService.update(idSearched, any(User.class));
        });

        verify(userDao, times(1)).getById(anyInt());
        verify(userDao, never()).getByUsername(anyString());
        verify(userDao, never()).update(anyInt(), any(User.class));
    }

    @Test
    void update_ThrowsException_WhenUpdateFails(){

        //Arrange

        String uniqueUsername = "newUser";

        //Arrange
        User user = new User(1,
                uniqueUsername,
                "passTest",
                new Timestamp(System.currentTimeMillis()));

        when(userDao.getById(1)).thenReturn(Optional.of(user));
        when(userDao.getByUsername(user.username())).thenReturn(Optional.empty());
        when(userDao.update(1, user)).thenReturn(0);

        //Act & Assert
        assertThrows(IllegalStateException.class, () -> {
            userService.update(1, user);
        });

        verify(userDao, times(1)).getByUsername(anyString());
        verify(userDao, times(1)).getById(anyInt());
        verify(userDao, times(1)).update(anyInt(), any(User.class));
    }

}