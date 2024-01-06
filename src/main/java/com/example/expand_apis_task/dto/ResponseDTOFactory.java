package com.example.expand_apis_task.dto;

import java.util.List;

public class ResponseDTOFactory {

    private ResponseDTOFactory() {
    }

    public static <T> ResponseDTO<T> getOkResponseDto(T data) {
        ResponseDTO<T> responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.OK);
        responseDto.setData(data);
        return responseDto;
    }

    public static <T> ResponseDTO<T> getFailedResponseDto(List<String> errors) {
        ResponseDTO<T> responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(errors);
        return responseDto;
    }
}