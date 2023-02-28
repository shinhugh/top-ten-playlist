package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.exception.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AccessDeniedAdvice {
    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    String exceptionHandler(AccessDeniedException ex) {
        return ex.getMessage();
    }
}
