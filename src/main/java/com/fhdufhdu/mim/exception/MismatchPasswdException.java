package com.fhdufhdu.mim.exception;

public class MismatchPasswdException extends RuntimeException {
    public MismatchPasswdException() {
        super("비밀번호가 틀렸습니다.");
    }
}
