package com.fhdufhdu.mim.exception;

public class NotFoundFavoriteMovieException extends RuntimeException {

    public NotFoundFavoriteMovieException() {
        super("즐겨찾기한 영화를 찾을 수 없습니다.");
    }

}
