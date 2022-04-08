package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import com.fhdufhdu.mim.entity.Genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByGenreName(String genreName);

    Optional<Genre> findByGenreName(String genreName);

    @Query(value = "select g from Genre g join g.genres mg join mg.movie m where m.id = :movieId", nativeQuery = false)
    List<Genre> findByMovieId(@Param("movieId") Long movieId);
}