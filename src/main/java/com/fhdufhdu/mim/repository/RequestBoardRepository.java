package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.RequestBoard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RequestBoardRepository extends JpaRepository<RequestBoard, Long> {
    @Query(value = "select r from RequestBoard r join r.movie m where r.isConfirmed = false", nativeQuery = false)
    Page<RequestBoard> findAll(Pageable pageable);

    @Query(value = "select r from RequestBoard r where r.id = :id and r.isConfirmed = false", nativeQuery = false)
    Optional<RequestBoard> findById(@Param("id") Long id);
}
