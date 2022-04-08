package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.MovieRating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    boolean existsByRating(String rating);

    Optional<MovieRating> findByRating(String rating);

    @Query(value = "select r from MovieRating r join r.movies m where m.id = :movidId", nativeQuery = false)
    Optional<MovieRating> findByMovieId(@Param("movidId") Long movidId);
}
