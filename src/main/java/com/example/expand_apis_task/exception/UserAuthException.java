package com.example.expand_apis_task.exception;

public class UserAuthException extends RuntimeException{

    public UserAuthException(String message){
        super(message);
    }

    public UserAuthException(String message, Throwable cause){
        super(message, cause);
    }
}
