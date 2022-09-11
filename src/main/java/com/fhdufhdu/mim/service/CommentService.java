package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.comment.CommentChange;
import com.fhdufhdu.mim.dto.comment.CommentInfo;
import com.fhdufhdu.mim.dto.comment.CommentWriting;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundMemberException;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.utils.ServiceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * GET /comments/post/{postId}?page={page}&size={size}
     * <p>
     * [repository 테스트 항목]
     * <ol>
     * <li>정렬이 잘 되는가?
     * </ol>
     */
    public Page<CommentInfo> getCommentsByPostId(Long postId, PageParam pageParam) {
        Sort sort = Sort.by("commentGroup", "depth", "time");
        return commentRepository.findByPostId(postId, pageParam.toPageRequest(sort));
    }

    /**
     * GET /posts/user/{userId}?page={page}&size={size}
     * <p>
     * [repository 테스트 항목]
     * <ol>
     * <li>정렬이 잘 되는가?
     * </ol>
     */
    public Page<CommentInfo> getCommentsByMemberId(String memberId, PageParam pageParam) {
        Sort sort = Sort.by("commentGroup", "depth", "time");
        return commentRepository.findByMemberId(memberId, pageParam.toPageRequest(sort));
    }

    /**
     * PUT /comments/{commentId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>댓글을 못 찾았을 때 예외가 발생하는가?
     * <li>데이터가 잘 바뀌는가?
     * <li>권한관련 예외가 잘 발생하는가?
     * </ol>
     */
    public CommentInfo changeComment(Long commentId, CommentChange comment) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);

        ServiceUtil.checkAdminMember(oldComment.getMember().getId());

        oldComment.setContent(comment.getContent());
        Comment newComment = commentRepository.save(oldComment);
        return ServiceUtil.convertToDest(newComment, CommentInfo.class);
    }

    /**
     * DELETE /comments/{commentId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>댓글 삭제 중 예외가 발생했을때 false를 반환하는가?
     * <li>댓글을 못찾으면 예외가 발생하는가?
     * <li>게시글의 댓글 수는 줄어드는가?
     * </ol>
     */
    public boolean removeComment(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
            ServiceUtil.checkAdminMember(comment.getMember().getId());
            Post post = comment.getPost();
            post.setCommentCnt(post.getCommentCnt() - 1);
            postRepository.save(post);
            commentRepository.delete(comment);
        } catch (NotFoundPostException e) {
            log.error(e.getMessage());
            return false;
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }

    /**
     * POST /comments
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>게시글을 못 찾았을 때 예외가 발생하는가?
     * <li>사용자를 못 찾았을 때 예외가 발생하는가?
     * <li>새로운 댓글 입력시 CommentGroup에 자신의 id가 들어가는가?
     * <li>새로운 대댓글 입력시 CommentGroup이 그대로 유지되는가?
     * <li>새로운 댓글 입력시 게시글의 댓글 수가 늘어나는가?
     * </ol>
     */
    public CommentInfo writeComment(CommentWriting comment) {
        Post post = postRepository.findById(comment.getPostId()).orElseThrow(NotFoundPostException::new);
        Member member = memberRepository.findById(comment.getMemberId()).orElseThrow(NotFoundMemberException::new);
        Comment newComment = Comment.builder()
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .commentGroup(comment.getCommentGroup())
                .content(comment.getContent())
                .depth(comment.getDepth())
                .post(post)
                .member(member)
                .build();
        newComment = commentRepository.save(newComment);
        if (newComment.getCommentGroup() == null)
            newComment.setCommentGroup(newComment.getId());
        // 게시글의 댓글 수 늘림
        post.setCommentCnt(post.getCommentCnt() + 1);
        postRepository.save(post);
        return ServiceUtil.convertToDest(commentRepository.save(newComment), CommentInfo.class);
    }
}
