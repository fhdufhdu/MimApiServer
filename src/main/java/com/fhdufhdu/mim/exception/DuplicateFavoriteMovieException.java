package com.fhdufhdu.mim.exception;

public class DuplicateFavoriteMovieException extends RuntimeException {
    public DuplicateFavoriteMovieException() {
        super("즐겨찾기가 이미 존재합니다.");
    }
}