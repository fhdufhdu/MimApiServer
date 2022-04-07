package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.CommentReportDto;
import com.fhdufhdu.mim.dto.PostingReportDto;
import com.fhdufhdu.mim.dto.ReportReasonDto;

public interface ReportService {
    /** POST /report-postings */
    void reportPosting(PostingReportDto postingReportDto);

    /** POST /report-comments */
    void reportComment(CommentReportDto commentReportDto);

    /** GET /report-reasons */
    List<ReportReasonDto> getAllReportReasons();

    /** GET /report-postings */
    List<PostingReportDto> getAllPostingReports();

    /** GET /report-comments */
    List<CommentReportDto> getAlCommentReports();

    /** PUT /report-postings/{id} */
    void confirmPostingReport(Long id);

    /** PUT /report-comments/{id} */
    void confirmCommentReport(Long id);
}
