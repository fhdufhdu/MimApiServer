package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.Movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select m from Movie m where m.title in (:titles) and m.id <> 1")
    List<Movie> findByTitleList(@Param("titles") List<String> titles);

    @Query(value = "select m from Movie m where m.id in (:ids) and m.id <> 1")
    List<Movie> findByIdList(@Param("ids") List<Long> ids);

    @Query(value = "select m from Movie m where m.title like %:title% and m.id <> 1")
    Page<Movie> findByTitle(@Param("title") String title, Pageable pageable);
}
