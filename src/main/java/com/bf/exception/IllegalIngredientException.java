package com.bf.exception;

public class IllegalIngredientException extends RuntimeException {
    public IllegalIngredientException(String msg) {
        super(msg);
    }
}
