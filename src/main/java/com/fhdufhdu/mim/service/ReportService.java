package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.CommentReportDto;
import com.fhdufhdu.mim.dto.PostingReportDto;
import com.fhdufhdu.mim.dto.ReportReasonDto;

import org.springframework.data.domain.Page;

public interface ReportService {
    /** POST /report-postings */
    void reportPosting(PostingReportDto postingReportDto);

    /** POST /report-comments */
    void reportComment(CommentReportDto commentReportDto);

    /** GET /report-reasons */
    Page<ReportReasonDto> getAllReportReasons(int page);

    /** GET /report-postings */
    Page<PostingReportDto> getAllPostingReports(int page);

    /** GET /report-comments */
    Page<CommentReportDto> getAllCommentReports(int page);

    /** PUT /report-postings/{id} */
    void confirmPostingReport(Long id);

    /** PUT /report-comments/{id} */
    void confirmCommentReport(Long id);
}
