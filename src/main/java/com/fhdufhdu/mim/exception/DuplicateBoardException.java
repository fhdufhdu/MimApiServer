package com.fhdufhdu.mim.exception;

public class DuplicateBoardException extends RuntimeException {
    public DuplicateBoardException() {
        super("해당 게시판이 이미 존재합니다.");
    }
}