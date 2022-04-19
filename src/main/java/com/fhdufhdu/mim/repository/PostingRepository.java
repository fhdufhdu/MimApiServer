package com.fhdufhdu.mim.repository;

import java.util.Optional;

import com.fhdufhdu.mim.entity.Posting;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    @Query(value = "select p from Posting p join p.postingId.movieBoard b where p.isRemoved = false and b.id = :boardId", nativeQuery = false)
    Page<Posting> findByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    @Query(value = "select p from Posting p join p.postingId.movieBoard b where p.isRemoved = flase and b.id = :boardId and p.postingNumber = :postingNumber", nativeQuery = false)
    Optional<Posting> findByBoardIdAndPostingNumber(@Param("boardId") Long boardId,
            @Param("postingNumber") Long postingNumber);

    // @Query(value = "delete from posting p where p.board_id = :boardId and
    // p.posting_number = :postingNumber", nativeQuery = true)
    // void deleteById(@Param("boardId") Long boardId, @Param("postingNumber") Long
    // postingNumber);
}
