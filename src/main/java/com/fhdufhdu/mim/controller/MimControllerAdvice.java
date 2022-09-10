package com.fhdufhdu.mim.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fhdufhdu.mim.exception.ExceptionResponse;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundMemberException;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
// 어떻게 하면 효율적으로 에러를 전달할 수 있을까
public class MimControllerAdvice {
    @ExceptionHandler(MismatchPasswdException.class)
    public ResponseEntity<ExceptionResponse> missmatchPasswdException(MismatchPasswdException e) {
        log.error(e.getMessage());
        ExceptionResponse response = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<ExceptionResponse> notFoundMemberException(NotFoundMemberException e) {
        log.error(e.getMessage());
        ExceptionResponse response = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
