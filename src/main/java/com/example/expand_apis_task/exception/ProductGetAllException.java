package com.example.expand_apis_task.exception;

public class ProductGetAllException extends RuntimeException {

    public ProductGetAllException(String message) {
        super(message);
    }

    public ProductGetAllException(String message, Throwable cause) {
        super(message, cause);
    }
}
