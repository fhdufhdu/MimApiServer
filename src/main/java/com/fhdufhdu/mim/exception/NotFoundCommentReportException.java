package com.fhdufhdu.mim.exception;

public class NotFoundCommentReportException extends RuntimeException {
    public NotFoundCommentReportException() {
        super("해당 댓글 신고를 찾을 수 없습니다.");
    }
}
