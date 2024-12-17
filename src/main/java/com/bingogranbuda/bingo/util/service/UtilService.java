package com.bingogranbuda.bingo.util.service;

import com.bingogranbuda.bingo.exception.DuplicateRecordException;

import java.util.Optional;
import java.util.function.Supplier;

public class UtilService {

    private UtilService(){}

    public static void verifyRowAffected(int daoAction, String errorMessage) {

        if(daoAction != 1)
            throw new IllegalStateException(errorMessage);
    }

    public static void verifyDuplication(Supplier<Optional<?>> supplier, String errorMessage){

        if(supplier.get().isPresent())
            throw new DuplicateRecordException(errorMessage);
    }

}
