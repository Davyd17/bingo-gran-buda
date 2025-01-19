package com.bingogranbuda.bingo.repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    List<T> getAll();

    Optional<T> getById(Integer id);

    Optional<T> insert(T entity);

    Optional<T> update(Integer id, T entity);

    int delete(Integer id);
}