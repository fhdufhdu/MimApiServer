package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.comment.ModifiedComment;
import com.fhdufhdu.mim.dto.comment.WrittenComment;
import com.fhdufhdu.mim.dto.post.ModifiedPost;
import com.fhdufhdu.mim.dto.post.PostDto;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.dto.post.WrittenPost;
import org.springframework.data.domain.Page;

import com.fhdufhdu.mim.dto.comment.CommentDto;

public interface PostCommentService {

    /** GET /posts?page={page}&size={size} */
    Page<PostListElem> getAllPosts(PageParam pageParam);

    /** GET /posts?query={query}&type={t|c|tc|u}&page={page}&size={size} */
    Page<PostListElem> getPostsByQuery(String query, String type, PageParam pageParam);

    /** GET /posts/{postId} */
    PostDto getPostById(Long id);

    /** PUT /posts/{postId} */
    void modifyPost(Long id, ModifiedPost post);

    /** DELETE /posts/{postId} */
    void removePost(Long id);

    /** POST /posts */
    void writePost(WrittenPost post);

    /** GET /comments/post/{postId}?page={page}&size={size} */
    Page<CommentDto> getCommentsByPostId(Long postId, PageParam pageParam);

    /** GET /posts/user/{userId}?page={page}&size={size} */
    Page<CommentDto> getCommentsByUserId(String userId, PageParam pageParam);

    /** PUT /comments/{commentId} */
    void modifyComment(Long commentId, ModifiedComment comment);

    /** DELETE /comments/{commentId} */
    void removeComment(Long commentId);

    /** POST /comments */
    void writeComment(WrittenComment comment);
}
