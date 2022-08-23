package com.fhdufhdu.mim.exception;

public class NotFoundPostException extends RuntimeException {
    public NotFoundPostException() {
        super("게시글을 찾을 수 없습니다.");
    }
}
