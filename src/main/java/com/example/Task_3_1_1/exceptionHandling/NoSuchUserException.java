package com.example.Task_3_1_1.exceptionHandling;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException(String message) {
        super(message);
    }
}