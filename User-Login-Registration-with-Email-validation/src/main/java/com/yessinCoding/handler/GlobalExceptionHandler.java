package com.yessinCoding.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {


    public ResponseEntity<ExceptionResponse>handleException(Exception e){
        ExceptionResponse response = new ExceptionResponse();
//        response.setErrorCode("Error");
//        response.setErrorMessage(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
