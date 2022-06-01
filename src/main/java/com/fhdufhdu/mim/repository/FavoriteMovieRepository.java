package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.FavoriteMovie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteMovieRepository extends JpaRepository<FavoriteMovie, Long> {

    // @Query(value = "select f from FavoriteMovie f join f.user u join f.movie m
    // where u.id =:userid", nativequery = false)
    Page<FavoriteMovie> findByUserId(String userId, Pageable pageable);

    Optional<FavoriteMovie> findByUserIdAndMovieId(String userId, Long movieId);

    void deleteByUserIdAndMovieId(String userId, Long movieId);

    boolean existsByUserIdAndMovieId(String userId, Long movieId);
}
