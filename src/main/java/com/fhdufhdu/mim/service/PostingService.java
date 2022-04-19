package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;

import org.springframework.data.domain.Page;

public interface PostingService {

    /** GET /postings/board/{boardId}?page={page} */
    Page<PostingDto> getAllPostings(Long boardId, int page);

    /** GET /postings/board/{boardId}/posting-number/{postingNumber} */
    PostingDto getPostingByBoardAndPostingNum(Long boardId, Long postingNumber);

    /** GET /postings/{postingId} */
    PostingDto getPostingById(Long id);

    /** PUT /postings/{postingId} */
    void modifyPosting(Long id, PostingDto postingDto);

    /** DELETE /postings/{postingId} */
    void removePosting(Long id);

    /** POST /postings */
    void addPosting(PostingDto postingDto);

    /**
     * GET
     * /comments/board/{boardId}/posting-number/{postingNumber}?page={page}
     */
    Page<CommentDto> getAllCommentsByBoardAndPostingNum(Long boardId, Long postingNumber, int page);

    /** GET /comments/posting/{postingId}?page={page} */
    Page<CommentDto> getAllCommentsByPostingId(Long postingId, int page);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, CommentDto commentDto);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** POST /comments */
    void addComment(CommentDto commentDto);
}
