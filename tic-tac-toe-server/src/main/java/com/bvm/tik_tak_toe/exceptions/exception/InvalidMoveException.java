package com.bvm.tik_tak_toe.exceptions.exception;

public class InvalidMoveException extends RuntimeException{
    public InvalidMoveException(String message) {
        super(message);
    }
}
