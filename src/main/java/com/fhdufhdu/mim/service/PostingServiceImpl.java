package com.fhdufhdu.mim.service;

import java.util.ArrayList;
import java.util.List;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;
import com.fhdufhdu.mim.entity.Board;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingId;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.MismatchAuthorException;
import com.fhdufhdu.mim.exception.NotFoundBoardException;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
// 페이징 구현해야함
public class PostingServiceImpl extends UtilService implements PostingService {
    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final static int POSTING_PAGE_SIZE = 10;
    private final static int COMMENT_PAGE_SIZE = 10;

    @Override
    public Page<PostingDto> getAllPostings(Long boardId, int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "postingId.commentCnt");
        PageRequest pageRequest = PageRequest.of(page, POSTING_PAGE_SIZE, sort);
        Page<Posting> sources = postingRepository.findByBoardId(boardId, pageRequest);
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

        checkUserId(originalPosting, getUserId());

        originalPosting.setTitle(postingDto.getTitle());
        originalPosting.setContent(postingDto.getContent());
        originalPosting.setTime(getNowTimestamp());
        postingRepository.save(originalPosting);
    }

    @Override
    public void removePosting(Long id) {
        Posting originalPosting = postingRepository.findById(id).orElseThrow(NotFoundPostingException::new);

        checkUserId(originalPosting, getUserId());

        originalPosting.setIsRemoved(true);
        postingRepository.save(originalPosting);
    }

    @Override
    public void addPosting(PostingDto postingDto) {
        Board board = boardRepository.findById(postingDto.getMovieBoardId())
                .orElseThrow(NotFoundBoardException::new);
        board.setLastPostingNumber(board.getLastPostingNumber() + 1);

        PostingId newPostingId = PostingId.builder()
                .movieBoard(board)
                .commentCnt(board.getLastPostingNumber())
                .build();

        User user = userRepository.findById(getUserId()).orElseThrow(NotFoundUserException::new);
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
    public Page<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber, int page) {
        List<Order> sortList = new ArrayList<>();
        sortList.add(Order.asc("commentGroup"));
        sortList.add(Order.asc("depth"));

        Sort sort = Sort.by(sortList);
        PageRequest pageRequest = PageRequest.of(page, COMMENT_PAGE_SIZE, sort);
        Page<Comment> comments = commentRepository.findByBoardIdAndPostingNum(boardId, postingNumber, pageRequest);
        return convertToDests(comments, CommentDto.class);
    }

    @Override
    public Page<CommentDto> getAllCommentsByPostingId(Long postingId, int page) {
        List<Order> sortList = new ArrayList<>();
        sortList.add(Order.asc("commentGroup"));
        sortList.add(Order.asc("depth"));

        Sort sort = Sort.by(sortList);
        PageRequest pageRequest = PageRequest.of(page, COMMENT_PAGE_SIZE, sort);
        Page<Comment> comments = commentRepository.findByPostingId(postingId, pageRequest);
        return convertToDests(comments, CommentDto.class);
    }

    @Override
    public void modifyComment(Long commentId, CommentDto commentDto) {
        Comment originalComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);

        checkUserId(originalComment, getUserId());

        originalComment.setContent(commentDto.getContent());
        originalComment.setTime(getNowTimestamp());
        commentRepository.save(originalComment);
    }

    @Override
    public void removeComment(Long commentId) {
        Comment originalComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);

        checkUserId(originalComment, getUserId());

        originalComment.setIsRemoved(true);
        commentRepository.save(originalComment);
    }

    @Override
    public void addComment(CommentDto commentDto) {
        Posting posting = postingRepository.findById(commentDto.getPostingId())
                .orElseThrow(NotFoundPostingException::new);
        User user = userRepository.findById(getUserId()).orElseThrow(NotFoundUserException::new);
        // Comment parentComment =
        // commentRepository.findById(commentDto.getCommentId()).get();

        Comment newComment = Comment.builder()
                .content(commentDto.getContent())
                .time(getNowTimestamp())
                .posting(posting)
                .commentGroup(100L)
                .depth(commentDto.getDepth())
                .user(user)
                .build();

        if (commentDto.getDepth() == 0) {
            Comment saved = commentRepository.save(newComment);
            saved.setCommentGroup(saved.getId());
            commentRepository.save(saved);
        } else {
            newComment.setCommentGroup(commentDto.getCommentGroup());
            commentRepository.save(newComment);
        }

    }

    private void checkUserId(Posting object, String userId) {
        if (!object.getUser().getId().equals(userId)) {
            throw new MismatchAuthorException();
        }
    }

    private void checkUserId(Comment object, String userId) {
        if (!object.getUser().getId().equals(userId)) {
            throw new MismatchAuthorException();
        }
    }
}
