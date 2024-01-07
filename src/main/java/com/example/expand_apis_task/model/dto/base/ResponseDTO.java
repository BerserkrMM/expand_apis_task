package com.example.expand_apis_task.model.dto.base;

public class ResponseDTO<T> extends BaseResponseDTO {

    private T data;

    public void setData(T data) {
        this.data=data;
    }

    public T getData() {
        return data;
    }
}
