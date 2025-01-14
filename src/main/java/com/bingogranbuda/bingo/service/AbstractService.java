package com.bingogranbuda.bingo.service;

import com.bingogranbuda.bingo.exception.NotFoundException;
import com.bingogranbuda.bingo.repository.Dao;
import com.bingogranbuda.bingo.util.service.UtilService;

import java.util.List;

public abstract class AbstractService<T> {

    protected final Dao<T> dao;

    public AbstractService(Dao<T> dao) {
        this.dao = dao;
    }

    protected abstract String getEntityTypeSimpleName();

    public List<T> findAll() {

        return dao.getAll();
    }

    public T findById(Integer id) {

        return dao.getById(id).
                orElseThrow(() -> new NotFoundException(
                        String.format("%s with ID %s not found, please enter a valid ID",
                                getEntityTypeSimpleName(),
                                id))
                );
    }

    public void create(T entity){

        UtilService.verifyRowAffected(
                dao.insert(entity),
                String.format("Oops something went wrong, %s couldn't be created", getEntityTypeSimpleName())
        );
    }

    public void delete(Integer id){

        findById(id);

        UtilService.verifyRowAffected(
                dao.delete(id),
                String.format("Oops something went wrong, %s couldn't be deleted", getEntityTypeSimpleName())
        );
    }

    public void update(Integer id, T newEntity){

        findById(id);

        UtilService.verifyRowAffected(
                dao.update(id, newEntity),
                String.format("Oops something went wrong, %s couldn't be updated", getEntityTypeSimpleName())
        );
    }
}
