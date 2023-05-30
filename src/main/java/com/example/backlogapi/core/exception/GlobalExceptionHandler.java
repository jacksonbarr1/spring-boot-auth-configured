package com.example.backlogapi.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleInvalidEmailException(InvalidEmailException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Invalid email");
        response.setE(e);
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return response;
    }

    @ExceptionHandler(EmailTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleEmailTakenException(EmailTakenException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Email already taken");
        response.setE(e);
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return response;
    }
}
