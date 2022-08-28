package com.fhdufhdu.mim.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fhdufhdu.mim.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query(value = "select m from Movie m where m.title in (:titles)")
    List<Movie> findByTitleList(@Param("titles") List<String> titles);

    @Query(value = "select m from Movie m where m.id in (:idList)")
    List<Movie> findByIdList(@Param("idList") List<Long> idList);

    @Query(value = "select m from Movie m where m.title like %:title%")
    Page<Movie> findByTitle(@Param("title") String title, Pageable pageable);
}
