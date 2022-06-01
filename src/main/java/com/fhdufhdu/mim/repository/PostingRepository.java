package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import com.fhdufhdu.mim.entity.Posting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query(value = "select p from Posting p join p.movieBoard b where p.isRemoved = false and b.id = :boardId", nativeQuery = false)
    Page<Posting> findByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    @Query(value = "select p from Posting p join p.movieBoard b where p.isRemoved = false and b.id = :boardId and p.title like %:query%", nativeQuery = false)
    Page<Posting> findByBoardIdAndQuery(@Param("boardId") Long boardId, @Param("query") String query,
            Pageable pageable);

    @Query(value = "select p from Posting p join p.movieBoard b where p.isRemoved = false and b.id = :boardId and p.postingNumber = :postingNumber", nativeQuery = false)
    Optional<Posting> findByBoardIdAndPostingNumber(@Param("boardId") Long boardId,
            @Param("postingNumber") Long postingNumber);

    // @EntityGraph(attributePaths = "postingId.movieBoard")
    @Query(value = "select p from Posting p where p.isRemoved = false and p.id = :id", nativeQuery = false)
    Optional<Posting> findById(@Param("id") Long id);

    @Query(value = "select p from Posting p join p.user u where p.isRemoved = false and u.id = :userId", nativeQuery = false)
    Page<Posting> findByUserId(@Param("userId") String userId, Pageable pageable);

    @EntityGraph(attributePaths = "movieBoard")
    @Query(value = "select p from Posting p join p.movieBoard b where p.isRemoved = false and p.id in (:ids) and b.isRemoved = false", nativeQuery = false)
    List<Posting> findByIds(@Param("ids") List<Long> ids);
}
