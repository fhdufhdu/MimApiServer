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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = { "게시글 신고 관리", "댓글 신고 관리" })
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/report-postings")
    @ApiOperation(value = "[등록] 게시글 신고")
    @Tag(name = "게시글 신고 관리")
    public void reportPosting(@RequestBody PostingReportSendDto postingReportDto) {
        reportService.reportPosting(postingReportDto);
    }

    @GetMapping("/report-postings")
    @ApiOperation(value = "모든 게시글신고 가져오기")
    @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    @Tag(name = "게시글 신고 관리")
    public Page<PostingReportDto> getAllPostingReports(int page) {
        return reportService.getAllPostingReports(page);
    }

    @PutMapping("/report-postings/{id}")
    @ApiOperation(value = "게시글 신고 처리완료")
    @ApiImplicitParam(name = "id", value = "게시글 신고 아이디", paramType = "path", required = true)
    @Tag(name = "게시글 신고 관리")
    public void confirmPostingReport(@PathVariable Long id) {
        reportService.confirmPostingReport(id);
    }

    @PostMapping("/report-comments")
    @ApiOperation(value = "댓글 신고")
    @Tag(name = "댓글 신고 관리")
    public void reportComment(@RequestBody CommentReportSendDto commentReportDto) {
        reportService.reportComment(commentReportDto);
    }

    @GetMapping("/report-comments")
    @ApiOperation(value = "모든 댓글신고 가져오기")
    @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    @Tag(name = "댓글 신고 관리")
    public Page<CommentReportDto> getAllCommentReports(int page) {
        return reportService.getAllCommentReports(page);
    }

    @PutMapping("/report-comments/{id}")
    @ApiOperation(value = "댓글 신고 처리완료")
    @ApiImplicitParam(name = "id", value = "게시글 신고 아이디", paramType = "path", required = true)
    @Tag(name = "댓글 신고 관리")
    public void confirmCommentReport(@PathVariable Long id) {
        reportService.confirmCommentReport(id);
    }
}
