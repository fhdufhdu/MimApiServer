package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.dto.PostSearchType;
import com.fhdufhdu.mim.dto.post.PostListElem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQueryDslRepository{
    Page<PostListElem> findByQuery(String query, PostSearchType type, Pageable pageable);

}
