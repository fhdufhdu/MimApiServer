package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query(value = "select c from Comment c join c.posting p join p.postingId.movieBoard b where c.isRemoved = false and p.postingId.postingNumber = :postingNumber and b.id = :boardId", nativeQuery = false)
    Page<Comment> findByBoardIdAndPostingNum(@Param("boardId") Long boardId,
            @Param("postingNumber") Long postingNumber, Pageable pageRequest);

    @Query(value = "select c from Comment c join c.posting p where c.isRemoved = false and p.id = :postingId", nativeQuery = false)
    Page<Comment> findByPostingId(@Param("postingId") Long postingId, Pageable pageable);

}
