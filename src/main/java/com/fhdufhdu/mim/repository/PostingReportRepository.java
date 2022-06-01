package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.PostingReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingReportRepository extends JpaRepository<PostingReport, Long> {
    @EntityGraph(attributePaths = "posting")
    @Query(value = "select r from PostingReport r join r.posting p where r.isConfirmed = false and p.isRemoved = false", nativeQuery = false)
    Page<PostingReport> findAll(Pageable pageable);

    @Query(value = "select r from PostingReport r join r.posting p where r.isConfirmed = false and r.id = :id and p.isRemoved = false", nativeQuery = false)
    Optional<PostingReport> findById(@Param("id") Long id);
}
