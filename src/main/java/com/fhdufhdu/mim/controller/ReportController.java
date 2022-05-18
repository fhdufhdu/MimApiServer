package com.fhdufhdu.mim.controller;

import com.fhdufhdu.mim.dto.report.CommentReportDto;
import com.fhdufhdu.mim.dto.report.CommentReportSendDto;
import com.fhdufhdu.mim.dto.report.PostingReportDto;
import com.fhdufhdu.mim.dto.report.PostingReportSendDto;
import com.fhdufhdu.mim.service.ReportService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report-postings")
    public void reportPosting(@RequestBody PostingReportSendDto postingReportDto) {
        reportService.reportPosting(postingReportDto);
    }

    @PostMapping("/report-comments")
    public void reportComment(@RequestBody CommentReportSendDto commentReportDto) {
        reportService.reportComment(commentReportDto);
    }

    @GetMapping("/report-postings")
    public Page<PostingReportDto> getAllPostingReports(int page) {
        return reportService.getAllPostingReports(page);
    }

    @GetMapping("/report-comments")
    public Page<CommentReportDto> getAllCommentReports(int page) {
        return reportService.getAllCommentReports(page);
    }

    @PutMapping("/report-postings/{id}")
    public void confirmPostingReport(@PathVariable Long id) {
        reportService.confirmPostingReport(id);
    }

    @PutMapping("/report-comments/{id}")
    public void confirmCommentReport(@PathVariable Long id) {
        reportService.confirmCommentReport(id);
    }
}
