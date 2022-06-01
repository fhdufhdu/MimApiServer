package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.CommentReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {
    @EntityGraph(attributePaths = "comment")
    @Query(value = "select r from CommentReport r where r.isConfirmed = false", nativeQuery = false)
    Page<CommentReport> findAll(Pageable pageable);

    @Query(value = "select r from CommentReport r where r.isConfirmed = false and r.id = :id", nativeQuery = false)
    Optional<CommentReport> findById(@Param("id") Long id);
}
