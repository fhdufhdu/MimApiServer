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

public interface PostRepository extends JpaRepository<Post, Long>, PostQueryDslRepository {
        @Query(value = "select new Post.ListElem(p.id, p.user.id, p.title, p.time, p.commentCnt) from Post p", nativeQuery = false)
        Page<Post.ListElem> findAllAtListElem(Pageable pageable);
}
