package com.bingogranbuda.bingo.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();

    Optional<T> getById(Integer id);

    int insert(T entity);

    int delete(Integer id);

    int update(Integer id, T entity);
}