package com.example.expand_apis_task.dto;

import java.util.List;

public class BaseResponseDTO {
    private ResponseDTOStatus responseDTOStatus;
    private List<String> errors;

    public ResponseDTOStatus getStatus() {
        return responseDTOStatus;
    }

    public void setStatus(ResponseDTOStatus responseDTOStatus) {
        this.responseDTOStatus = responseDTOStatus;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
