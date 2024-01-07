package com.example.expand_apis_task.exception;

import com.example.expand_apis_task.model.dto.base.ResponseDTO;
import com.example.expand_apis_task.model.enums.ResponseDTOStatus;
import jakarta.servlet.ServletException;
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

import java.util.List;

import static com.example.expand_apis_task.model.dto.base.ResponseDTOFactory.getFailedResponseDto;

@RestControllerAdvice
public class ExpandApisTaskControllerAdvice {

    private static final Logger LOG = LoggerFactory.getLogger("ControllerAdvice");

    @ExceptionHandler(Exception.class)
    ResponseEntity<ResponseDTO<?>> handleOtherException(Exception error) {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_JSON_VALUE));

        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(
                List.of("Other Exception handled", error.getMessage(), error.getCause() != null ? error.getCause().getMessage() : "no cause"));

        LOG.error("handleException message: {}", error.getMessage(), error);
        return new ResponseEntity<>(responseDto, httpHeaders, HttpStatus.OK);
    }

//    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
//    public ResponseDTO<?> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex, HttpServletResponse response) {
//        var responseDTO = new ResponseDTO<>();
//        responseDTO.setErrors(List.of("AuthenticationCredentialsNotFoundException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        return responseDTO;
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseDTO<?> handleAccessDeniedException(AccessDeniedException ex, HttpServletResponse response) {
//        var responseDTO = new ResponseDTO<>();
//        responseDTO.setErrors(List.of("AccessDeniedException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
//        response.setStatus(HttpStatus.FORBIDDEN.value());
//        return responseDTO;
//    }
//
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseDTO<?> handleAuthenticationException(AuthenticationException ex, HttpServletResponse response) {
//        var responseDTO = new ResponseDTO<>();
//        responseDTO.setErrors(List.of("AuthenticationException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        return responseDTO;
//    }


    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ResponseDTO<?>> handleAuthenticationCredentialsNotFoundException(AuthenticationCredentialsNotFoundException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("AuthenticationCredentialsNotFoundException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseDTO<?>> handleAccessDeniedException(AccessDeniedException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("AccessDeniedException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseDTO<?>> handleAuthenticationException(AuthenticationException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("AuthenticationException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO<?>> handleIllegalArgumentException(IllegalArgumentException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("IllegalArgumentException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO<?>> handleBadCredentialsException(BadCredentialsException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("BadCredentialsException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserAddException(UserAddException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("UserAddException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthException.class)
    public ResponseEntity<ResponseDTO<?>> handleUserAuthException(UserAuthException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("UserAuthException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TableCreationException.class)
    public ResponseEntity<ResponseDTO<?>> handleTableCreationException(TableCreationException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("TableCreationException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAddException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductAddException(ProductAddException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("ProductAddException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("HttpMessageNotReadableException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JwtGeneratorException.class)
    public ResponseEntity<ResponseDTO<?>> handleJwtGeneratorException(JwtGeneratorException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("JwtGeneratorException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductGetAllException.class)
    public ResponseEntity<ResponseDTO<?>> handleProductGetAllException(ProductGetAllException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("ProductGetAllException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletException.class)
    public ResponseEntity<ResponseDTO<?>> handleServletException(ServletException ex) {
        var responseDto = new ResponseDTO<>();
        responseDto.setStatus(ResponseDTOStatus.FAILED);
        responseDto.setErrors(List.of("ServletException handled", ex.getMessage(), ex.getCause() != null ? ex.getCause().getMessage() : "no cause"));
        LOG.error("{} handled", ex.getClass().getSimpleName());
        return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
    }
}
