package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.util.List;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.MovieBoard;
import com.fhdufhdu.mim.entity.Posting;
import com.fhdufhdu.mim.entity.PostingId;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.NotFoundBoardException;
import com.fhdufhdu.mim.exception.NotFoundPostingException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.BoardRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostingRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.mapper.MapperService;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingServiceImpl extends MapperService implements PostingService {
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
        originalPosting.setTime(new Timestamp(System.currentTimeMillis()));
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
                .time(new Timestamp(System.currentTimeMillis()))
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
    public List<CommentDto> getAllCommentsByPostingId(Long id) {

        return null;
    }

    @Override
    public void modifyComment(Long commentId, CommentDto commentDto) {

    }

    @Override
    public void removeComment(Long commentId) {

    }

    @Override
    public void addComment(CommentDto commentDto) {

    }

}
