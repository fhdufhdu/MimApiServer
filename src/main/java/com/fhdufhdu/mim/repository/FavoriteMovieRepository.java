package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.FavoriteMovie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

    // @Query(value = "select f from FavoriteMovie f join f.user u where u.id =
    // :userId", nativeQuery = false)
    Page<FavoriteMovie> findMovieByUserId(String userId, Pageable pageable);

    boolean existsByUserIdAndMovieId(String userId, Long movieId);
}
