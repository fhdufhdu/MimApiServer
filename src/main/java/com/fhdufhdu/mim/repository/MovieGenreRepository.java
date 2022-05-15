package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.MovieGenre;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieGenreRepository extends JpaRepository<MovieGenre, Long> {

    void deleteByMovieId(Long movieId);
}
