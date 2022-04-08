package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.MovieBoard;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingId;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.NotFoundBoardException;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PostingServiceImpl extends UtilService implements PostingService {
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<PostingDto> getAllPostings(Long boardId) {
        List<Posting> sources = postingRepository.findByBoardId(boardId);
        return convertToDests(sources, PostingDto.class);
    }

    @Override
    public PostingDto getPostingByBoardAndPostingNum(Long boardId, Long postingNumber) {
        Posting source = postingRepository.findByBoardIdAndPostingNumber(boardId, postingNumber)
                .orElseThrow(NotFoundPostingException::new);
        return convertToDest(source, PostingDto.class);
    }

    @Override
    public PostingDto getPostingById(Long id) {
        Posting source = postingRepository.findById(id).orElseThrow(NotFoundPostingException::new);
        return convertToDest(source, PostingDto.class);
    }

    @Override
    public void modifyPosting(Long id, PostingDto postingDto) {
        Posting originalPosting = postingRepository.findById(id).orElseThrow(NotFoundPostingException::new);
        originalPosting.setTitle(postingDto.getTitle());
        originalPosting.setContent(postingDto.getContent());
        originalPosting.setTime(getNowTimestamp());
        postingRepository.save(originalPosting);
    }

    @Override
    public void removePosting(Long id) {
        postingRepository.deleteById(id);
    }

    @Override
    public void addPosting(PostingDto postingDto) {
        MovieBoard board = boardRepository.findById(postingDto.getMovieBoardId())
                .orElseThrow(NotFoundBoardException::new);
        PostingId newPostingId = PostingId.builder()
                .movieBoard(board)
                .commentCnt(0)
                .build();
        User user = userRepository.findById(postingDto.getUserId()).orElseThrow(NotFoundUserException::new);
        Posting newPosting = Posting.builder()
                .postingId(newPostingId)
                .title(postingDto.getTitle())
                .content(postingDto.getContent())
                .time(getNowTimestamp())
                .user(user)
                .build();
        postingRepository.save(newPosting);
    }

    @Override
    public List<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber) {
        List<Comment> comments = commentRepository.findByBoardIdAndPostingNum(boardId, postingNumber);
        return convertToDests(comments, CommentDto.class);
    }

    @Override
    public List<CommentDto> getAllCommentsByPostingId(Long postingId) {
        List<Comment> comments = commentRepository.findByPostingId(postingId);
        return convertToDests(comments, CommentDto.class);
    }

    @Override
    public void modifyComment(Long commentId, CommentDto commentDto) {
        Comment originalComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        originalComment.setContent(commentDto.getContent());
        originalComment.setTime(getNowTimestamp());
        commentRepository.save(originalComment);
    }

    @Override
    public void removeComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Override
    public void addComment(CommentDto commentDto) {
        Posting posting = postingRepository.findById(commentDto.getPostingId())
                .orElseThrow(NotFoundPostingException::new);
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(NotFoundUserException::new);
        Comment parentComment = commentRepository.findById(commentDto.getCommentId()).get();

        Comment newComment = Comment.builder()
                .content(commentDto.getContent())
                .time(getNowTimestamp())
                .posting(posting)
                .user(user)
                .comment(parentComment)
                .build();

        commentRepository.save(newComment);
    }

}
