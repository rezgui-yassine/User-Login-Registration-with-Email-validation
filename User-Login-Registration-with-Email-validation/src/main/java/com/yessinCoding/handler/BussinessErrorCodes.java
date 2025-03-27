package com.yessinCoding.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.security.SecureRandom;

import static org.springframework.http.HttpStatus.*;

public enum BussinessErrorCodes {
    NoCode(0, NOT_IMPLEMENTED, "No CODE"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect current password"),
    NEW_PASSWORD_DOES_NOT_MATCH (301, BAD_REQUEST, "THE NEW PASSWORD DOES NOT MATCH"),
    ACCOUNT_LOCKED(302 , FORBIDDEN, "Account is locked"),
    ACCOUNT_DISABLEDD(303 , FORBIDDEN, "USER ACCOUNT IS DISABLED"),
    BAD_CREDENTIALS(304, UNAUTHORIZED, "BAD CREDENTIALS LOGIN AND / OR PASSWORD INCORRECT"),


    ;
    @Getter
    private final int code;
    @Getter
    private final String description;
    @Getter
    private final HttpStatus httpStatus;

    BussinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
