package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.CommentReportDto;
import com.fhdufhdu.mim.dto.PostingReportDto;
import com.fhdufhdu.mim.dto.ReportReasonDto;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.CommentReport;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingReport;
import com.fhdufhdu.mim.entity.ReportReason;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundReasonException;
import com.fhdufhdu.mim.repository.CommentReportRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingReportRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
import com.fhdufhdu.mim.repository.ReportReasonRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportServiceImpl extends UtilService implements ReportService {
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
    public List<ReportReasonDto> getAllReportReasons() {
        List<ReportReason> reasons = reportReasonRepository.findAll();
        return convertToDests(reasons, ReportReasonDto.class);
    }

    @Override
    public List<PostingReportDto> getAllPostingReports() {
        List<PostingReport> reports = postingReportRepository.findAll();
        return convertToDests(reports, PostingReportDto.class);
    }

    @Override
    public List<CommentReportDto> getAlCommentReports() {
        List<CommentReport> reports = commentReportRepository.findAll();
        return convertToDests(reports, CommentReportDto.class);
    }

    @Override
    public void confirmPostingReport(Long id) {
        postingReportRepository.deleteById(id);

    }

    @Override
    public void confirmCommentReport(Long id) {
        commentReportRepository.deleteById(id);
    }

}
