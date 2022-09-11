package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.PostSearchType;
import com.fhdufhdu.mim.dto.post.PostChange;
import com.fhdufhdu.mim.dto.post.PostInfo;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.dto.post.PostWriting;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.utils.ServiceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    // 게시글

    /**
     * GET /posts?page={page}&size={size}
     * <p>
     * [repository 테스트 항목]
     * <ol>
     * <li>정렬이 잘되는지</li>
     * </ol>
     */
    // 아래 함수와 중복되는 로직임
    // public Page<PostListElem> getAllPosts(PageParam pageParam) {
    // Sort sort = Sort.by("id").descending();
    // return postRepository.findAllAtListElem(pageParam.toPageRequest(sort));
    // }

    /**
     * GET
     * /posts?query={query}&type={title|content|title_content|user}&page={page}&size={size}
     * <p>
     * [repository 테스트 항목]
     * <ol>
     * <li>정렬이 잘되는지, 타입에 맞게 검색이 잘 되는지</li>
     * </ol>
     */
    public Page<PostListElem> getPostsByQuery(String query, String type, PageParam pageParam) {
        Sort sort = Sort.by("id").descending();
        return postRepository.findByQuery(query, PostSearchType.valueOf(type),
                pageParam.toPageRequest(sort));
    }

    /**
     * GET /posts/{postId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>게시글 못 찾았을 때 예외 발생하는지
     * </ol>
     */
    public PostInfo getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        return ServiceUtil.convertToDest(post, PostInfo.class);
    }

    /**
     * PUT /posts/{postId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>게시글 못 찾았을 때 예외 발생하는지
     * <li>게시글 수정이 잘 되었는지
     * </ol>
     */
    public PostInfo modifyPost(Long id, PostChange post) {
        Post oldPost = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
        ServiceUtil.checkAdminMember(oldPost.getMember().getId());
        oldPost.setContent(post.getContent());
        oldPost.setTitle(post.getTitle());
        Post newPost = postRepository.save(oldPost);
        return ServiceUtil.convertToDest(newPost, PostInfo.class);
    }

    /**
     * DELETE /posts/{postId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>게시글 삭제에서 오류 발생시 catch문으로 이동하는지
     * </ol>
     */
    public boolean removePost(Long id) {
        try {
            Post post = postRepository.findById(id).orElseThrow(NotFoundPostException::new);
            ServiceUtil.checkAdminMember(post.getMember().getId());
            postRepository.delete(post);
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }

    /**
     * POST /posts
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>멤버를 못찾을때 예외가 발생하는지
     * <li>게시글 등록시 댓글 카운트 값이 0인지
     * </ol>
     */
    public PostInfo writePost(PostWriting post) {
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
