package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query(value = "select b from Board b where b.isRemoved = false and b.id != 1", nativeQuery = false)
    Page<Board> findAll(Pageable pageable);

    @Query(value = "select b from Board b where b.id = :id and b.isRemoved = false", nativeQuery = false)
    Optional<Board> findById(@Param("id") Long id);

    @Query(value = "select b from Board b join b.movie m where m.title like %:title% and b.isRemoved = false", nativeQuery = false)
    Page<Board> findByMovieTitle(@Param("title") String title, Pageable pageable);

    @Query(value = "select b from Board b join b.movie m where m.id = :movieId and b.isRemoved = false", nativeQuery = false)
    Optional<Board> findByMovieId(@Param("movieId") Long movieId);

    @EntityGraph(attributePaths = "movie")
    @Query(value = "select b from Board b where b.id in (:ids) and b.isRemoved = false", nativeQuery = false)
    List<Board> findByIds(@Param("ids") List<Long> ids);
}
