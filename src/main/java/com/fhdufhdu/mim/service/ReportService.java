package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.CommentReport;
import com.fhdufhdu.mim.entity.PostingReport;
import com.fhdufhdu.mim.entity.ReportReason;

public interface ReportService {
    void reportPosting(Long postingId, Long reasonId);

    void reportComment(Long commentId, Long reasonId);

    List<ReportReason> getAllReportReasons();

    List<PostingReport> getAllPostingReports();

    List<CommentReport> getAlCommentReports();

    void confirmPostingReport(Long id);

    void confirmCommentReport(Long id);
}
