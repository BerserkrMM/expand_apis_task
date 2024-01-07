package com.example.expand_apis_task.exception;

public class TableCreationException extends RuntimeException {

    public TableCreationException(String message) {
        super(message);
    }

    public TableCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
