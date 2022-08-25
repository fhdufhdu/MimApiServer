package com.fhdufhdu.mim.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select new Comment.Info(c.id, p.id, c.user.id, c.commentGroup, c.depth, c.content, c.time) from Comment c join c.post p where p.id = :postId", nativeQuery = false)
    Page<Comment.Info> findByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query(value = "select c.id from Comment c join c.post p where p.id = :postId", nativeQuery = false)
    List<Long> findByPostId(@Param("postId") Long postId);

    @Query(value = "select new Comment.Info(c.id, c.post.id, u.id, c.commentGroup, c.depth, c.content, c.time) from Comment c join c.user u where u.id = :userId", nativeQuery = false)
    Page<Comment.Info> findByUserId(@Param("userId") String userId, Pageable pageable);
}
