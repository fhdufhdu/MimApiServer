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
    @Query(value = "select c from Comment c join c.posting p join p.movieBoard b where c.isRemoved = false and p.postingNumber = :postingNumber and b.id = :boardId", nativeQuery = false)
    Page<Comment> findByBoardIdAndPostingNum(@Param("boardId") Long boardId,
            @Param("postingNumber") Long postingNumber, Pageable pageRequest);

    @Query(value = "select c from Comment c join c.posting p where c.isRemoved = false and p.id = :postingId", nativeQuery = false)
    Page<Comment> findByPostingId(@Param("postingId") Long postingId, Pageable pageable);

    @Query(value = "select c from Comment c join c.user u where c.isRemoved = false and u.id = :userId", nativeQuery = false)
    Page<Comment> findByUserId(@Param("userId") String userId, Pageable pageable);

    @EntityGraph(attributePaths = "posting")
    @Query(value = "select c from Comment c join c.posting p where c.isRemoved = false and c.id in (:ids) and p.isRemoved = false", nativeQuery = false)
    List<Comment> findByIds(@Param("ids") List<Long> ids);
}
