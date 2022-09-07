package com.example.newscoccer.exception;

public class NotFoundEntity extends RuntimeException{
    public NotFoundEntity(String message) {
        super(message);
    }
}
