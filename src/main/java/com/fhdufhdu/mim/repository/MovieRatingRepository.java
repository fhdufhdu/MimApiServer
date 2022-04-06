package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.MovieRating;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    boolean existsByRating(String rating);
}
