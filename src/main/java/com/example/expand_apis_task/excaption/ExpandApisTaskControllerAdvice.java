package com.example.expand_apis_task.excaption;

import com.example.expand_apis_task.dto.ResponseDTO;
import com.example.expand_apis_task.dto.ResponseDTOStatus;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static com.example.expand_apis_task.dto.ResponseDTOFactory.getFailedResponseDto;

@RestControllerAdvice
public class ExpandApisTaskControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger("ControllerAdvice");

    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> otherExceptionHandler(Exception error) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));
        LOG.error("handleException message: {}", error.getMessage(), error);
        return new ResponseEntity<>(getFailedResponseDto(List.of(error.getMessage())), httpHeaders, HttpStatus.OK);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseDTO<?> handleAuthenticationException(AuthenticationCredentialsNotFoundException ex, HttpServletResponse response) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>();
        responseDTO.setErrors(List.of("хто ти, воїн?", ex.getMessage()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return responseDTO;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseDTO<?> handleAuthenticationException(AccessDeniedException ex, HttpServletResponse response) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>();
        responseDTO.setErrors(List.of("незя...", ex.getMessage()));
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return responseDTO;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseDTO<?> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response) {
        ResponseDTO<?> responseDTO = new ResponseDTO<>();
        responseDTO.setErrors(List.of("хто ти, воїн?", ex.getMessage()));
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return responseDTO;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of(ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<?>> handleBadCredentialsException(BadCredentialsException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of(ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserAddException(UserAddException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("ну куди...", ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserAuthException(UserAuthException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("протри очі...", ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProductCreationException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductCreationException(ProductCreationException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("крива таблиця...", ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAddException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductAddException(ProductAddException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("тухляк не пройде...", ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("ніц нема...", ex.getMessage()));
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
