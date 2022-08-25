package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PostQueryDslRepository{
    Page<Post.ListElem> findByQuery(String query, String type, Pageable pageable);
}
