package com.fhdufhdu.mim.exception;

public class NotFoundReasonException extends RuntimeException {
    public NotFoundReasonException() {
        super("신고사유를 찾을 수 없습니다.");
    }
}
