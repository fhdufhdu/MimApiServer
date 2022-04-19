package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.CommentReportDto;
import com.fhdufhdu.mim.dto.PostingReportDto;
import com.fhdufhdu.mim.dto.ReportReasonDto;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.CommentReport;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingReport;
import com.fhdufhdu.mim.entity.ReportReason;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundCommentReportException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundPostingReportException;
import com.fhdufhdu.mim.exception.NotFoundReasonException;
import com.fhdufhdu.mim.repository.CommentReportRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingReportRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
import com.fhdufhdu.mim.repository.ReportReasonRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl extends UtilService implements ReportService {
    private static final int PAGE_SIZE = 10;
    private final ReportReasonRepository reportReasonRepository;
    private final PostingReportRepository postingReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;

    @Override
    public void reportPosting(PostingReportDto postingReportDto) {
        Posting posting = postingRepository.findById(postingReportDto.getPostingId())
                .orElseThrow(NotFoundPostingException::new);
        ReportReason reason = reportReasonRepository.findById(postingReportDto.getReportReasonId())
                .orElseThrow(NotFoundReasonException::new);
        PostingReport newReport = PostingReport.builder()
                .posting(posting)
                .reportReason(reason)
                .build();

        postingReportRepository.save(newReport);
    }

    @Override
    public void reportComment(CommentReportDto commentReportDto) {
        Comment comment = commentRepository.findById(commentReportDto.getCommentId())
                .orElseThrow(NotFoundCommentException::new);
        ReportReason reason = reportReasonRepository.findById(commentReportDto.getReportReasonId())
                .orElseThrow(NotFoundReasonException::new);
        CommentReport newReport = CommentReport.builder()
                .comment(comment)
                .reportReason(reason)
                .build();

        commentReportRepository.save(newReport);

    }

    @Override
    public Page<ReportReasonDto> getAllReportReasons(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "reason");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<ReportReason> reasons = reportReasonRepository.findAll(pageRequest);
        return convertToDests(reasons, ReportReasonDto.class);
    }

    @Override
    public Page<PostingReportDto> getAllPostingReports(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "reportTimestamp");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<PostingReport> reports = postingReportRepository.findAll(pageRequest);
        return convertToDests(reports, PostingReportDto.class);
    }

    @Override
    public Page<CommentReportDto> getAllCommentReports(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "reportTimestamp");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<CommentReport> reports = commentReportRepository.findAll(pageRequest);
        return convertToDests(reports, CommentReportDto.class);
    }

    @Override
    public void confirmPostingReport(Long id) {
        // postingReportRepository.deleteById(id);
        PostingReport report = postingReportRepository.findById(id).orElseThrow(NotFoundPostingReportException::new);
        report.setIsConfirmed(true);
        postingReportRepository.save(report);
    }

    @Override
    public void confirmCommentReport(Long id) {
        // commentReportRepository.deleteById(id);
        CommentReport report = commentReportRepository.findById(id).orElseThrow(NotFoundCommentReportException::new);
        report.setIsConfirmed(true);
        commentReportRepository.save(report);
    }

}
