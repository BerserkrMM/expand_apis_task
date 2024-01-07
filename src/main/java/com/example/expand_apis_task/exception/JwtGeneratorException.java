package com.example.expand_apis_task.exception;

public class JwtGeneratorException extends RuntimeException{

    public JwtGeneratorException(String message){
        super(message);
    }

    public JwtGeneratorException(String message, Throwable cause){
        super(message, cause);
    }
}
