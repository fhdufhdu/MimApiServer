package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class PostQueryDslRepositoryImpl implements PostQueryDslRepository{
    public Page<Post.ListElem> findByQuery(String query, String type, Pageable pageable){
        return null;
    }
}
