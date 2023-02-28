package org.dev.toptenplaylist.controller;

import org.dev.toptenplaylist.model.ElementAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ElementAlreadyExistsAdvice {
    @ResponseBody
    @ExceptionHandler(ElementAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String exceptionHandler(ElementAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
