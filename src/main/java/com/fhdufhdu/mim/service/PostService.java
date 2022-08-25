package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    // 게시글
    /** GET /posts?page={page}&size={size} */
    Page<Post.ListElem> getAllPosts(PageParam pageParam){
        Sort sort = Sort.by("id").descending();
        Page<Post.ListElem> posts = postRepository.findAllAtListElem(pageParam.toPageRequest(sort));
        return posts;
    }

    /** GET /posts?query={query}&type={t|c|tc|u}&page={page}&size={size} */
    Page<Post.ListElem> getPostsByQuery(String query, String type, PageParam pageParam){
        /** querydsl 써야함 */
        Sort sort = Sort.by("id").descending();
        Page<Post.ListElem> posts = postRepository.findByQuery(query, type, pageParam.toPageRequest(sort));
        return posts;
    }

    /** GET /posts/{postId} */
    Post.Info getPostById(Long id){
        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        return ServiceUtil.convertToDest(post, Post.Info.class);
    }

    /** PUT /posts/{postId} */
    Post.Info modifyPost(Long id, Post.Change post){
        Post oldPost = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        oldPost.setContent(post.getContent());
        oldPost.setTitle(post.getTitle());
        Post newPost = postRepository.save(oldPost);
        return ServiceUtil.convertToDest(newPost, Post.Info.class);
    }

    /** DELETE /posts/{postId} */
    boolean removePost(Long id){
        try{
            //댓글 모두 삭제
            List<Long> commentIdList = commentRepository.findByPostId(id);
            commentIdList.stream().forEach(c -> commentRepository.deleteById(c));

            //게시글 삭제
            postRepository.deleteById(id);
        }catch (Exception e){
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }

    /** POST /posts */
    Post.Info writePost(Post.Writing post){
        User user = userRepository.findById(post.getUserId()).orElseThrow(NotFoundPostException::new);
        Post newPost = Post.builder()
                .user(user)
                .commentCnt(0)
                .content(post.getContent())
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .title(post.getTitle())
                .build();
        newPost = postRepository.save(newPost);
        return ServiceUtil.convertToDest(newPost, Post.Info.class);
    }
}
