package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.PostSearchType;
import com.fhdufhdu.mim.dto.post.PostChange;
import com.fhdufhdu.mim.dto.post.PostInfo;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.dto.post.PostWriting;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.repository.PostRepository;
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


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    // 게시글

    /**
     * GET /posts?page={page}&size={size}
     * [repository 테스트 항목]
     * 1. 정렬이 잘되는지
     */
    Page<PostListElem> getAllPosts(PageParam pageParam) {
        Sort sort = Sort.by("id").descending();
        Page<PostListElem> posts = postRepository.findAllAtListElem(pageParam.toPageRequest(sort));
        return posts;
    }

    /**
     * GET /posts?query={query}&type={title|content|title_content|user}&page={page}&size={size}
     * [repository 테스트 항목]
     * 1-1.정렬이 잘되는지
     * 1-2.타입에 맞게 검색이 잘 되는지
     */
    Page<PostListElem> getPostsByQuery(String query, String type, PageParam pageParam) {
        Sort sort = Sort.by("id").descending();
        Page<PostListElem> posts = postRepository.findByQuery(query, PostSearchType.valueOf(type), pageParam.toPageRequest(sort));
        return posts;
    }

    /**
     * GET /posts/{postId}
     * [service 테스트 항목]
     * 1. 게시글 못 찾았을 때 예외 발생하는지
     */
    PostInfo getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        return ServiceUtil.convertToDest(post, PostInfo.class);
    }

    /**
     * PUT /posts/{postId}
     * [service 테스트 항목]
     * 1. 게시글 못 찾았을 때 예외 발생하는지
     * 2. 게시글 수정이 잘 되었는지
     */
    PostInfo modifyPost(Long id, PostChange post) {
        Post oldPost = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        oldPost.setContent(post.getContent());
        oldPost.setTitle(post.getTitle());
        Post newPost = postRepository.save(oldPost);
        return ServiceUtil.convertToDest(newPost, PostInfo.class);
    }

    /**
     * DELETE /posts/{postId}
     * [service 테스트 항목]
     * 1. 게시글 삭제에서 오류 발생시 catch문으로 이동하는지
     */
    boolean removePost(Long id) {
        try {
            postRepository.deleteById(id);
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }

    /**
     * POST /posts
     * [service 테스트 항목]
     * 1. 멤버를 못찾을때 예외가 발생하는지
     * 2. 게시글 등록시 댓글 카운트 값이 0인지
     */
    PostInfo writePost(PostWriting post) {
        Member member = memberRepository.findById(post.getMemberId()).orElseThrow(NotFoundPostException::new);
        Post newPost = Post.builder()
                .member(member)
                .commentCnt(0)
                .content(post.getContent())
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .title(post.getTitle())
                .build();
        newPost = postRepository.save(newPost);
        return ServiceUtil.convertToDest(newPost, PostInfo.class);
    }
}
