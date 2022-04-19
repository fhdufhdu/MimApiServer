package com.fhdufhdu.mim.exception;

public class NotFoundPostingReportException extends RuntimeException {
    public NotFoundPostingReportException() {
        super("해당 게시글 신고를 찾을 수 없습니다.");
    }
}
