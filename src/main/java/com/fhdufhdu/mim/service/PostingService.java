package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.comment.CommentAddDto;
import com.fhdufhdu.mim.dto.comment.CommentDto;
import com.fhdufhdu.mim.dto.comment.CommentModifyDto;
import com.fhdufhdu.mim.dto.posting.PostingAddDto;
import com.fhdufhdu.mim.dto.posting.PostingDto;
import com.fhdufhdu.mim.dto.posting.PostingModifyDto;

import org.springframework.data.domain.Page;

public interface PostingService {

    /** GET /postings/board/{boardId}?page={page} */
    Page<PostingDto> getAllPostings(Long boardId, int page);

    /** GET /postings/board/{boardId}/posting-number/{postingNumber} */
    PostingDto getPostingByBoardAndPostingNum(Long boardId, Long postingNumber);

    /** GET /postings/{postingId} */
    PostingDto getPostingById(Long id);

    /** GET /postings/user/{userId} */
    Page<PostingDto> getPostingsByUserId(String userId, int page);

    /** PUT /postings/{postingId} */
    void modifyPosting(Long id, PostingModifyDto postingDto);

    /** DELETE /postings/{postingId} */
    void removePosting(Long id);

    /** POST /postings */
    void addPosting(PostingAddDto postingDto);

    /**
     * GET
     * /comments/board/{boardId}/posting-number/{postingNumber}?page={page}
     */
    Page<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber, int page);

    /** GET /comments/posting/{postingId}?page={page} */
    Page<CommentDto> getAllCommentsByPostingId(Long postingId, int page);

    /** GET /postings/user/{userId} */
    Page<CommentDto> getCommentsByUserId(String userId, int page);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, CommentModifyDto commentDto);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** POST /comments */
    void addComment(CommentAddDto commentDto);
}
