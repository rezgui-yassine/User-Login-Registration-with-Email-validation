package com.yessinCoding.handler;

import jakarta.mail.MessagingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.security.DigestException;
import java.util.HashSet;
import java.util.Set;

import static com.yessinCoding.handler.BussinessErrorCodes.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice // this annotation is used to handle exceptions globally
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse>handleException(LockedException e){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BussinessErrorCodes.ACCOUNT_LOCKED.getCode())
                        .businessErrorDescription(BussinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                        .error(e.getMessage())
                        .build());
    }

    @ExceptionHandler(DigestException.class)
    public ResponseEntity<ExceptionResponse>handleException(DisabledException e){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BussinessErrorCodes.ACCOUNT_DISABLEDD.getCode())
                        .businessErrorDescription(BussinessErrorCodes.ACCOUNT_DISABLEDD.getDescription())
                        .error(e.getMessage())
                        .build());
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse>handleException(BadCredentialsException e){
        return ResponseEntity.status(UNAUTHORIZED)
                .body(ExceptionResponse.builder()
                        .businessErrorCode(BAD_CREDENTIALS.getCode())
                        .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                        .error(BAD_CREDENTIALS.getDescription())
                        .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse>handleException(MessagingException e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .error(e.getMessage())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse>handleException(MethodArgumentNotValidException e){
        Set<String>errors =new HashSet<>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity.status(BAD_REQUEST)
                .body(ExceptionResponse.builder()
                        .validationsError(errors)
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse>handleException(Exception e){

        e.printStackTrace();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(ExceptionResponse.builder()
                        .businessErrorDescription("Internal Server Error , Contact the administrator")
                        .error(e.getMessage())
                        .build());
    }
}
