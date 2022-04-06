package com.fhdufhdu.mim.exception;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException() {
        super("해당 아이디가 이미 존재합니다.");
    }
}
