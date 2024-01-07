package com.example.expand_apis_task.exception;

public class UserAddException extends RuntimeException{

    public UserAddException(String message){
        super(message);
    }

    public UserAddException(String message, Throwable cause){
        super(message, cause);
    }
}
