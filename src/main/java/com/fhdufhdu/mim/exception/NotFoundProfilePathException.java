package com.fhdufhdu.mim.exception;

public class NotFoundProfilePathException extends RuntimeException {

    public NotFoundProfilePathException() {
        super("해당 유저의 프로필을 찾을 수 없습니다.");
    }

}
