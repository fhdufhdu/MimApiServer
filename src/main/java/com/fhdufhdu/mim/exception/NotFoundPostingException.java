package com.fhdufhdu.mim.exception;

public class NotFoundPostingException extends RuntimeException {
    public NotFoundPostingException() {
        super("게시글을 찾을 수 없습니다.");
    }
}
