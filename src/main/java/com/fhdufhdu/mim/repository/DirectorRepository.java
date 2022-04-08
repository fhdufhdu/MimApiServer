package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import com.fhdufhdu.mim.entity.worker.Director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DirectorRepository extends JpaRepository<Director, Long> {
    boolean existsByName(String directorName);

    Optional<Director> findByName(String directorName);

    @Query(value = "select d from Director d join d.workers md join md.movie m where m.id = :movieId", nativeQuery = false)
    List<Director> findByMovieId(@Param("movieId") Long movieId);
}
