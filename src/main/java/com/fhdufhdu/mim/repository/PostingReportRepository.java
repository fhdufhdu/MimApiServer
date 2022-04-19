package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.PostingReport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingReportRepository extends JpaRepository<PostingReport, Long> {
    @Query(value = "select r from PostingReport r where r.isConfirmed = false", nativeQuery = false)
    Page<PostingReport> findAll(Pageable pageable);

    @Query(value = "select r from PostingReport r where r.isConfirmed = false and r.id = :id", nativeQuery = false)
    Optional<PostingReport> findById(@Param("id") Long id);
}
