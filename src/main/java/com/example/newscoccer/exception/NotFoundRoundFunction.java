package com.example.newscoccer.exception;

public class NotFoundRoundFunction extends RuntimeException{
    public NotFoundRoundFunction(String message) {
        super(message);
    }

    public NotFoundRoundFunction(String message, Throwable cause) {
        super(message, cause);
    }
}
