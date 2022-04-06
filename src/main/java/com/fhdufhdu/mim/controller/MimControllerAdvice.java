package com.fhdufhdu.mim.controller;

import java.util.Date;

import com.fhdufhdu.mim.exception.ExceptionResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class MimControllerAdvice {
    @ExceptionHandler(RuntimeException.class)
    public ExceptionResponse anyException(Exception e) {
        log.error(e.getMessage());
        return new ExceptionResponse(new Date(), null, e.getMessage());
    }
}
