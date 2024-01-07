package com.example.expand_apis_task.model.enums;

public enum ResponseDTOStatus {
    OK(1),
    FAILED(2);

    public final Integer statusCode;

    ResponseDTOStatus(Integer statusCode) {
        this.statusCode = statusCode;
    }
}