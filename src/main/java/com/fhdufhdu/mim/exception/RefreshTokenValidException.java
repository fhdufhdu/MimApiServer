package com.fhdufhdu.mim.exception;

public class RefreshTokenValidException extends RuntimeException {
    public RefreshTokenValidException() {
        super("Access Token 재발급 실패");
    }
}
