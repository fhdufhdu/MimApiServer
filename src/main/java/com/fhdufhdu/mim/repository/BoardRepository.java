package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.MovieBoard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<MovieBoard, Long> {
    @Query(value = "select b from MovieBoard b join b.movie m where m.title = :title", nativeQuery = false)
    List<MovieBoard> findByMovieTitle(@Param("title") String title);

    @Query(value = "select b from MovieBoard b join b.movie m where m.id = :movieId", nativeQuery = false)
    MovieBoard findByMovieId(@Param("movieId") Long movieId);
}
