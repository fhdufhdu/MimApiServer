package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.MovieWorker;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieWorkerRepository extends JpaRepository<MovieWorker, Long> {
    void deleteByMovieId(Long movidId);

    List<MovieWorker> findByMovieId(Long movieId);
}
