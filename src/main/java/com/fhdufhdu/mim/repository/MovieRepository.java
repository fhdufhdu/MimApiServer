package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.Movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select m from Movie m where m.title in (:titles)", nativeQuery = false)
    List<Movie> findByTitieList(@Param("titles") List<String> titles);
}
