package com.fhdufhdu.mim.repository;

import java.util.List;

import com.fhdufhdu.mim.entity.worker.Writer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    boolean existsByName(String writerName);

    List<Writer> findByName(String writerName);

    @Query(value = "select w from Writer w join w.workers mw join mw.movie m where m.id = :movieId", nativeQuery = false)
    List<Writer> findByMovieId(@Param("movieId") Long movieId);
}
