package com.demo.fakestoreproductservice4.controllers;

import com.demo.fakestoreproductservice4.dtos.ErrorDto;
import com.demo.fakestoreproductservice4.exceptions.ClientErrorException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handling Exception: Method 2 - Exception Advices
 * @ControllerAdvice
 * - Enables code re-usability
 */
@ControllerAdvice
public class ExceptionAdvices {
    @ExceptionHandler(ClientErrorException.class)
    public HttpEntity<ErrorDto> handleClientErrorException(ClientErrorException e) {
        return new ResponseEntity<>(new ErrorDto(e.getErrCode(), e.getErrMsg()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
