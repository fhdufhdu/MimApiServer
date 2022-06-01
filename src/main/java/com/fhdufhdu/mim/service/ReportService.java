package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.report.CommentReportDto;
import com.fhdufhdu.mim.dto.report.CommentReportSendDto;
import com.fhdufhdu.mim.dto.report.PostingReportDto;
import com.fhdufhdu.mim.dto.report.PostingReportSendDto;

import org.springframework.data.domain.Page;

public interface ReportService {
    /** POST /report-postings */
    void reportPosting(PostingReportSendDto postingReportDto);

    /** POST /report-comments */
    void reportComment(CommentReportSendDto commentReportDto);

    /** GET /report-postings */
    Page<PostingReportDto> getAllPostingReports(int page, int size);

    /** GET /report-comments */
    Page<CommentReportDto> getAllCommentReports(int page, int size);

    /** PUT /report-postings/{id} */
    void confirmPostingReport(Long id);

    /** PUT /report-comments/{id} */
    void confirmCommentReport(Long id);
}
