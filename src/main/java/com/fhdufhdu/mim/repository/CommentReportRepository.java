package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.entity.CommentReport;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    @EntityGraph(attributePaths = "comment")
    @Query(value = "select r from CommentReport r join r.comment c join c.posting p join p.movieBoard b where r.isConfirmed = false and c.isRemoved = false and p.isRemoved = false and b.isRemoved = false", nativeQuery = false)
    Page<CommentReport> findAll(Pageable pageable);

    @Query(value = "select r from CommentReport r join r.comment c where r.isConfirmed = false and r.id = :id and c.isRemoved = false", nativeQuery = false)
    Optional<CommentReport> findById(@Param("id") Long id);

    @Query(value = "select r from CommentReport r join r.comment c where r.isConfirmed = false and c.id = :commentId", nativeQuery = false)
    List<CommentReport> findByCommentId(@Param("commentId") Long commentId);
}
