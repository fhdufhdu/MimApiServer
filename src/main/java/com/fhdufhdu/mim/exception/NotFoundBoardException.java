package com.fhdufhdu.mim.exception;

public class NotFoundBoardException extends RuntimeException {
    public NotFoundBoardException() {
        super("게시판을 찾을 수 없습니다.");
    }
}
