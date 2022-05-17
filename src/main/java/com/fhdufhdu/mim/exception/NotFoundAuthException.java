package com.fhdufhdu.mim.exception;

public class NotFoundAuthException extends RuntimeException {
    public NotFoundAuthException() {
        super("로그인을 진행하지 않았거나 로그아웃되었습니다.");
    }
}
