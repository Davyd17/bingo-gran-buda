package com.bingogranbuda.bingo.repository.user;

import com.bingogranbuda.bingo.model.User;
import com.bingogranbuda.bingo.repository.Dao;

import java.util.Optional;

public interface UserDao extends Dao<User> {

    public Optional<User> getByUsername(String username);
}