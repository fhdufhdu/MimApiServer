package com.fhdufhdu.mim.exception;

public class WrongPermissionException extends RuntimeException {
    public WrongPermissionException() {
        super("올바른 권한이 부여되지 않았습니다.");
    }
}
