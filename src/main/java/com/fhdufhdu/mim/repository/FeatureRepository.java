package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.Feature;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
    boolean existsByFeatureName(String featureName);

    List<Feature> findByFeatureName(String featureName);

    @Query(value = "select f from Feature f join f.features fg join fg.movie m where m.id = :movieId", nativeQuery = false)
    List<Feature> findByMovieId(@Param("movieId") Long movieId);
}
