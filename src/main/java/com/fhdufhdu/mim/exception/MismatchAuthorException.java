package com.fhdufhdu.mim.exception;

public class MismatchAuthorException extends RuntimeException {
    public MismatchAuthorException() {
        super("작성자가 일치하지 않습니다.");
    }
}
