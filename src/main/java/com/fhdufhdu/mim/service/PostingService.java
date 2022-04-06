package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Posting;

public interface PostingService {
    /** GET boards/{boardId}/postings */
    List<Posting> getAllPostings(Long boardId);

    /** GET boards/{boardId}/postings/{postingNumber} */
    Posting getPosting(Long boardId, Long postingNumber);

    /**  */
    void modifyPosting(String userId, Long boardId, Long postingNumber, Posting posting);

    /**  */
    void removePosting(String userId, Long boardId, Long postingNumber);

    /**  */
    void addPosting(String userId, Posting posting);

    /**  */
    List<Comment> getAllComments(Long boardId, Long postingNumber);

    /**  */
    void modifyComment(String userId, Comment comment);

    /**  */
    void removeComment(String userId, Long commentId);

    /**  */
    void addComment(Comment comment);
}
