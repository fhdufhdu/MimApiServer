package com.fhdufhdu.mim.exception;

public class MismatchAuthorException extends RuntimeException {
    public MismatchAuthorException() {
        super("사용자 혹은 권한이 올바르지 않습니다.");
    }
}