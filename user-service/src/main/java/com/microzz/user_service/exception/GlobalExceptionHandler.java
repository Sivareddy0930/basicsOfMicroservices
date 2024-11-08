package com.microzz.user_service.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotExistWithIdException.class)
    public ResponseEntity<ExceptionResponse> userNotExist(UserNotExistWithIdException exception){
        String timestamp = new java.util.Date().toString();
        ExceptionResponse response = new ExceptionResponse(timestamp,404, exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception ex){
        String timestamp = new java.util.Date().toString();
        ExceptionResponse response = new ExceptionResponse(timestamp,500, ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
