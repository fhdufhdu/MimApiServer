package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.util.List;

import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.exception.MismatchAuthorException;
import com.fhdufhdu.mim.exception.NoFoundPostingException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingRepository;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl implements PostingService {
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;

    /** 모든 데이터 가져오기 */
    @Override
    public List<Posting> getAllPostings(Long boardId) {
        return postingRepository.findAll();
    }

    /**
     * URI에서 데이터 받아와서 게시글 조회(그래서 파라미터가 MovieBoard 객체가 아님)
     * param : board id, posting number
     * return : Posting 객체
     */
    @Override
    public Posting getPosting(Long boardId, Long postingNumber) {
        return postingRepository.findByPostingId(boardId, postingNumber).orElseThrow(NoFoundPostingException::new);
    }

    /**
     * 게시글 수정, 수정된 게시글 객체와 사용자 아이디를 입력으로 받음
     * param : user id, Posting 객체
     * return : void
     */
    @Override
    public void modifyPosting(String userId, Long boardId, Long postingNumber, Posting posting) {
        if (!isAuthor(userId, posting)) {
            throw new MismatchAuthorException();
        }
        Timestamp current = new Timestamp(System.currentTimeMillis());
        Posting modifiedPosting = postingRepository.findById(postingNumber)
                .map(x -> {
                    x.setContent(posting.getContent());
                    x.setPostingNumber(posting.getPostingNumber());
                    x.setTime(current);
                    x.setTitle(posting.getTitle());
                    x.setUser(posting.getUser());
                    return x;
                })
                .orElseThrow(NoFoundPostingException::new);

        postingRepository.save(modifiedPosting);
    }

    /**
     * 게시글을 제거함, uri로 게시판 아이디, 게시글 번호 받아와서 제거
     * param : user id, board id, posting number
     * return : void
     */
    @Override
    public void removePosting(String userId, Long boardId, Long postingNumber) {
        postingRepository.deleteById(boardId, postingNumber);
    }

    private boolean isAuthor(String userId, Posting posting) {
        if (userId.equals(posting.getUser().getId())) {
            return true;
        }
        return false;
    }

    @Override
    public void addPosting(String userId, Posting posting) {
        postingRepository.save(posting);
    }

    @Override
    public List<Comment> getAllComments(Long boardId, Long postingNumber) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void modifyComment(String userId, Comment comment) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeComment(String userId, Long commentId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void addComment(Comment comment) {
        // TODO Auto-generated method stub

    }

    private boolean isAuthor(String userId, Comment comment) {
        // TODO Auto-generated method stub
        return false;
    }

}
