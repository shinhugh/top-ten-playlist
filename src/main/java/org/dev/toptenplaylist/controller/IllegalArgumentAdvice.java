package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.exception.IllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IllegalArgumentAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String exceptionHandler(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
