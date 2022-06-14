package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.entity.worker.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    boolean existsByName(String actorName);

    Optional<Actor> findByName(String actorName);

    @Query(value = "select a from Actor a join a.workers ma join ma.movie m where m.id = :movieId", nativeQuery = false)
    List<Actor> findByMovieId(@Param("movieId") Long movieId);
}