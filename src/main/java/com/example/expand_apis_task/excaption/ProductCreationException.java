package com.example.expand_apis_task.excaption;

public class ProductCreationException extends RuntimeException {

    public ProductCreationException(String message) {
        super(message);
    }

    public ProductCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
