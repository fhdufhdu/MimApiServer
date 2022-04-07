package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;

public interface PostingService {

    /** GET /postings?board-id={boardId} */
    List<PostingDto> getAllPostings(Long boardId);

    /** GET /postings?board-id={boardId}&posting-number={postingNumber} */
    PostingDto getPostingByBoardAndPostingNum(Long boardId, Long postingNumber);

    /** GET /posting/{postingId} */
    PostingDto getPostingById(Long id);

    /** PUT /postings/{postingId} */
    void modifyPosting(Long id, PostingDto postingDto);

    /** DELETE /postings/{postingId} */
    void removePosting(Long id);

    /** POST /postings */
    void addPosting(PostingDto postingDto);

    /** GET /comments?board-id={boardId}&posting-number={postingNumber} */
    List<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber);

    /** GET /comments?posting-id={postingId} */
    List<CommentDto> getAllCommentsByPostingId(Long id);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, CommentDto commentDto);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** POST /comments */
    void addComment(CommentDto commentDto);
}
