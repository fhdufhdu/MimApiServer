package com.fhdufhdu.mim.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.dto.comment.CommentInfo;
import com.fhdufhdu.mim.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select new com.fhdufhdu.mim.dto.comment.CommentInfo(c.id, p.id, c.member.id, c.commentGroup, c.depth, c.content, c.time) from Comment c join c.post p where p.id = :postId")
    Page<CommentInfo> findByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query(value = "select c.id from Comment c join c.post p where p.id = :postId")
    List<Long> findByPostId(@Param("postId") Long postId);

    @Query(value = "select new com.fhdufhdu.mim.dto.comment.CommentInfo(c.id, c.post.id, m.id, c.commentGroup, c.depth, c.content, c.time) from Comment c join c.member m where m.id = :memberId")
    Page<CommentInfo> findByMemberId(@Param("memberId") String memberId, Pageable pageable);
}
