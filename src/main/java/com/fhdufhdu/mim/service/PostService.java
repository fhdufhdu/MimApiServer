package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Post;
import org.springframework.data.domain.Page;


public interface PostService {
    // 게시글
    /** GET /posts?page={page}&size={size} */
    Page<Post.ListElem> getAllPosts(PageParam pageParam);

    /** GET /posts?query={query}&type={t|c|tc|u}&page={page}&size={size} */
    Page<Post.ListElem> getPostsByQuery(String query, String type, PageParam pageParam);

    /** GET /posts/{postId} */
    Post.Info getPostById(Long id);

    /** PUT /posts/{postId} */
    void modifyPost(Long id, Post.Change post);

    /** DELETE /posts/{postId} */
    void removePost(Long id);

    /** POST /posts */
    void writePost(Post.Writing post);
}
