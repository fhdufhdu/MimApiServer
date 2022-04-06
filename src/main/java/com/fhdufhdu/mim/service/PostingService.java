package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Posting;

public interface PostingService {
    /** GET /boards/{boardId}/postings */
    List<Posting> getAllPostings(Long boardId);

    /** GET /boards/{boardId}/postings/{postingNumber} */
    Posting getPosting(Long boardId, Long postingNumber);

    /** PUT /boards/{boardId}/postings/{postingNumber} */
    void modifyPosting(Long boardId, Long postingNumber, Posting posting);

    /** DELETE /boards/{boardId}/postings/{postingNumber} */
    void removePosting(Long boardId, Long postingNumber);

    /** POST /boards/{boardId}/postings */
    void addPosting(Posting posting);

    /** GET /boards/{boardId}/postings/{postNumber}/comments */
    List<Comment> getAllComments(Long boardId, Long postingNumber);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, Comment comment);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** PUT /boards/{boardId}/postings/{postingNumber}/comments */
    void addComment(Long boardId, Long postingNumber, Comment comment);
}
