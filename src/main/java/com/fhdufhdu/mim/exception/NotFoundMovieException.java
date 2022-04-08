package com.fhdufhdu.mim.exception;

public class NotFoundMovieException extends RuntimeException {
    public NotFoundMovieException() {
        super("영화를 찾을 수 없습니다.");
    }
}
