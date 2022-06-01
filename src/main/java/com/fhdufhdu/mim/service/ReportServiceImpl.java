package com.fhdufhdu.mim.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.board.BoardDto;
import com.fhdufhdu.mim.dto.comment.CommentDto;
import com.fhdufhdu.mim.dto.posting.PostingDto;
import com.fhdufhdu.mim.dto.report.CommentReportDto;
import com.fhdufhdu.mim.dto.report.CommentReportSendDto;
import com.fhdufhdu.mim.dto.report.PostingReportDto;
import com.fhdufhdu.mim.dto.report.PostingReportSendDto;
import com.fhdufhdu.mim.entity.Board;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.CommentReport;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingReport;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.MismatchAuthorException;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundCommentReportException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundPostingReportException;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.CommentReportRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingReportRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
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
    private final PostingReportRepository postingReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Override
    public void reportPosting(PostingReportSendDto postingReportDto) {
        Posting posting = postingRepository.findById(postingReportDto.getPostingId())
                .orElseThrow(NotFoundPostingException::new);
        PostingReport newReport = PostingReport.builder()
                .posting(posting)
                .reportReason(postingReportDto.getReportReason())
                .reportTimestamp(getNowTimestamp())
                .isConfirmed(false)
                .build();

        postingReportRepository.save(newReport);
    }

    @Override
    public void reportComment(CommentReportSendDto commentReportDto) {
        Comment comment = commentRepository.findById(commentReportDto.getCommentId())
                .orElseThrow(NotFoundCommentException::new);
        CommentReport newReport = CommentReport.builder()
                .comment(comment)
                .reportReason(commentReportDto.getReportReason())
                .reportTimestamp(getNowTimestamp())
                .isConfirmed(false)
                .build();

        commentReportRepository.save(newReport);

    }

    @Override
    public Page<PostingReportDto> getAllPostingReports(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "reportTimestamp");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<PostingReport> reports = postingReportRepository.findAll(pageRequest);

        Map<Long, Long> postingIds = new HashMap<>();
        Map<Long, Long> boardIds = new HashMap<>();

        Map<Long, Posting> postingMap = new HashMap<>();
        Map<Long, Board> boardMap = new HashMap<>();

        reports.forEach(x -> postingIds.put(x.getPosting().getId(), x.getId()));
        List<Posting> postings = postingRepository.findByIds(new ArrayList<>(postingIds.keySet()));
        postings.forEach(x -> {
            boardIds.put(x.getMovieBoard().getId(), postingIds.get(x.getId()));
            postingMap.put(postingIds.get(x.getId()), x);
        });
        List<Board> boards = boardRepository.findByIds(new ArrayList<>(boardIds.keySet()));
        boards.forEach(x -> {
            boardMap.put(boardIds.get(x.getId()), x);
        });

        Page<PostingReportDto> reportsDto = reports.map((x) -> {
            Long reportId = x.getId();
            Posting posting = postingMap.get(reportId);
            Board board = boardMap.get(reportId);
            Movie movie = board.getMovie();

            PostingReportDto dto = getModelMapper().map(x, PostingReportDto.class);
            dto.setPostingDto(getModelMapper().map(posting, PostingDto.class));
            dto.setBoardDto(getModelMapper().map(board, BoardDto.class));
            dto.setMovieDto(getModelMapper().map(movie, MovieDto.class));
            return dto;
        });
        return reportsDto;
    }

    @Override
    public Page<CommentReportDto> getAllCommentReports(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "reportTimestamp");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<CommentReport> reports = commentReportRepository.findAll(pageRequest);

        Map<Long, Long> commentIds = new HashMap<>();
        Map<Long, Long> postingIds = new HashMap<>();
        Map<Long, Long> boardIds = new HashMap<>();

        Map<Long, Comment> commentMap = new HashMap<>();
        Map<Long, Posting> postingMap = new HashMap<>();
        Map<Long, Board> boardMap = new HashMap<>();

        reports.forEach(x -> commentIds.put(x.getComment().getId(), x.getId())); // <댓글아이디, 신고아이디>
        List<Comment> comments = commentRepository.findByIds(new ArrayList<>(commentIds.keySet()));
        comments.forEach(x -> {
            postingIds.put(x.getPosting().getId(), commentIds.get(x.getId())); // <게시글아이디, 신고아이디>
            commentMap.put(commentIds.get(x.getId()), x); // <신고아이디, 댓글객체>
        });
        List<Posting> postings = postingRepository.findByIds(new ArrayList<>(postingIds.keySet()));
        postings.forEach(x -> {
            boardIds.put(x.getMovieBoard().getId(), postingIds.get(x.getId()));
            postingMap.put(postingIds.get(x.getId()), x);
        });
        List<Board> boards = boardRepository.findByIds(new ArrayList<>(boardIds.keySet()));
        boards.forEach(x -> {
            boardMap.put(boardIds.get(x.getId()), x);
        });

        Page<CommentReportDto> reportsDto = reports.map((x) -> {
            Long reportId = x.getId();
            Comment comment = commentMap.get(reportId);
            Posting posting = postingMap.get(reportId);
            Board board = boardMap.get(reportId);
            Movie movie = board.getMovie();

            CommentReportDto dto = getModelMapper().map(x, CommentReportDto.class);
            dto.setCommentDto(getModelMapper().map(comment, CommentDto.class));
            dto.setPostingDto(getModelMapper().map(posting, PostingDto.class));
            dto.setBoardDto(getModelMapper().map(board, BoardDto.class));
            dto.setMovieDto(getModelMapper().map(movie, MovieDto.class));
            return dto;
        });

        return reportsDto;
    }

    @Override
    public void confirmPostingReport(Long id) {
        // postingReportRepository.deleteById(id);
        if (!hasAuthority(Role.ADMIN)) {
            throw new MismatchAuthorException();
        }
        PostingReport report = postingReportRepository.findById(id).orElseThrow(NotFoundPostingReportException::new);
        report.setIsConfirmed(true);

        Posting originalPosting = report.getPosting();
        originalPosting.setIsRemoved(true);

        report.setPosting(originalPosting);
        postingReportRepository.save(report);
    }

    @Override
    public void confirmCommentReport(Long id) {
        // commentReportRepository.deleteById(id);
        if (!hasAuthority(Role.ADMIN)) {
            throw new MismatchAuthorException();
        }
        CommentReport report = commentReportRepository.findById(id).orElseThrow(NotFoundCommentReportException::new);
        report.setIsConfirmed(true);

        Comment comment = report.getComment();
        comment.setIsRemoved(true);

        report.setComment(comment);
        commentReportRepository.save(report);
    }

}
