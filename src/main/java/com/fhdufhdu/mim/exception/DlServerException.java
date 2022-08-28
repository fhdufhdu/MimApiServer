package com.fhdufhdu.mim.exception;

public class DlServerException extends RuntimeException {
    public DlServerException() {
        super("딥러닝 서버에서 데이터를 불러오는 것에 문제가 있습니다.");
    }
}
