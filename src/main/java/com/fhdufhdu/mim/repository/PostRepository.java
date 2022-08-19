package com.fhdufhdu.mim.repository;

import java.util.List;
import java.util.Optional;

import com.fhdufhdu.mim.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
        @Query(value = "select p from Post p join p.movieBoard b where p.isRemoved = false and b.id = :boardId", nativeQuery = false)
        Page<Post> findByBoardId(@Param("boardId") Long boardId, Pageable pageable);

        @Query(value = "select p from Post p join p.movieBoard b where p.isRemoved = false and b.id = :boardId and p.title like %:query%", nativeQuery = false)
        Page<Post> findByBoardIdAndQuery(@Param("boardId") Long boardId, @Param("query") String query,
                                         Pageable pageable);

        @Query(value = "select p from Post p join p.movieBoard b where p.isRemoved = false and b.id = :boardId and p.postingNumber = :postingNumber", nativeQuery = false)
        Optional<Post> findByBoardIdAndPostingNumber(@Param("boardId") Long boardId,
                                                     @Param("postingNumber") Long postingNumber);

        // @EntityGraph(attributePaths = "postingId.movieBoard")
        @Query(value = "select p from Post p where p.isRemoved = false and p.id = :id", nativeQuery = false)
        Optional<Post> findById(@Param("id") Long id);

        @EntityGraph(attributePaths = "movieBoard")
        @Query(value = "select p from Post p join p.user u where p.isRemoved = false and u.id = :userId", nativeQuery = false)
        Page<Post> findByUserId(@Param("userId") String userId, Pageable pageable);

        @EntityGraph(attributePaths = "movieBoard")
        @Query(value = "select p from Post p join p.movieBoard b where p.isRemoved = false and p.id in (:ids) and b.isRemoved = false", nativeQuery = false)
        List<Post> findByIds(@Param("ids") List<Long> ids);
}
