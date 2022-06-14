package com.fhdufhdu.mim.service;

import org.springframework.data.domain.Page;

import com.fhdufhdu.mim.dto.comment.CommentAddDto;
import com.fhdufhdu.mim.dto.comment.CommentDto;
import com.fhdufhdu.mim.dto.comment.CommentModifyDto;
import com.fhdufhdu.mim.dto.posting.PostingAddDto;
import com.fhdufhdu.mim.dto.posting.PostingDto;
import com.fhdufhdu.mim.dto.posting.PostingModifyDto;
import com.fhdufhdu.mim.dto.posting.PostingUserDto;

public interface PostingService {

    /** GET /postings/board/{boardId}?page={page} */
    Page<PostingDto> getAllPostings(Long boardId, int page, int size);

    /** GET /postings/board/{boardId}/posting-number/{postingNumber} */
    PostingDto getPostingByBoardAndPostingNum(Long boardId, Long postingNumber);

    /** GET /postings/board/{boardId}/query?query={query} */
    Page<PostingDto> getPostingByQuery(Long boardId, String query, int page, int size);

    /** GET /postings/{postingId} */
    PostingDto getPostingById(Long id);

    /** GET /postings/user/{userId} */
    Page<PostingUserDto> getPostingsByUserId(String userId, int page, int size);

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
    Page<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber, int page, int size);

    /** GET /comments/posting/{postingId}?page={page} */
    Page<CommentDto> getAllCommentsByPostingId(Long postingId, int page, int size);

    /** GET /postings/user/{userId} */
    Page<CommentDto> getCommentsByUserId(String userId, int page, int size);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, CommentModifyDto commentDto);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** POST /comments */
    void addComment(CommentAddDto commentDto);
}
