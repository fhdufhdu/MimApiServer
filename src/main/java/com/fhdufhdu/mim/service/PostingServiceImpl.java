package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.util.List;

import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService {
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<Posting> getAllPostings(Long boardId) {
        return postingRepository.findByBoardId(boardId);
    }

    @Override
    public Posting getPosting(Long boardId, Long postingNumber) {
        return postingRepository.findByPostingId(boardId, postingNumber).orElseThrow(NotFoundPostingException::new);
    }

    @Override
    public void modifyPosting(Long boardId, Long postingNumber, Posting posting) {
        Posting originalPosting = getPosting(boardId, postingNumber);
        originalPosting.clone(posting);
        postingRepository.save(originalPosting);
    }

    @Override
    public void removePosting(Long boardId, Long postingNumber) {
        Posting originalPosting = getPosting(boardId, postingNumber);
        postingRepository.delete(originalPosting);
    }

    @Override
    public void addPosting(Posting posting) {
        posting.setTime(new Timestamp(System.currentTimeMillis()));
        postingRepository.save(posting);
    }

    @Override
    public List<Comment> getAllComments(Long boardId, Long postingNumber) {
        return commentRepository.findByPostingId(boardId, postingNumber);
    }

    @Override
    public void modifyComment(Long commentId, Comment comment) {
        Comment originalComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
    }

    @Override
    public void removeComment(Long commentId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addComment(Long boardId, Long postingNumber, Comment comment) {
        // TODO Auto-generated method stub

    }

}
