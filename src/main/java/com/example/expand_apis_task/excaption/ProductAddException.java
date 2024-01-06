package com.example.expand_apis_task.excaption;

public class ProductAddException extends RuntimeException {

    public ProductAddException(String message) {
        super(message);
    }

    public ProductAddException(String message, Throwable cause) {
        super(message, cause);
    }
}
