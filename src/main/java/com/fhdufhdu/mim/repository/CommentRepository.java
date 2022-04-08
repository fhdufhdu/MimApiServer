package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c join c.posting p join p.postingId.movieBoard b where p.postingNumber = :postingNumber and b.id = :boardId", nativeQuery = false)
    List<Comment> findByBoardIdAndPostingNum(@Param("boardId") Long boardId,
            @Param("postingNumber") Long postingNumber);

    @Query(value = "select c from Comment c join c.posting p where p.id = :postingId", nativeQuery = false)
    List<Comment> findByPostingId(@Param("postingId") Long postingId);
}
