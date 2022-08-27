package com.fhdufhdu.mim.service;

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
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * GET /comments/post/{postId}?page={page}&size={size}
     * [repository 테스트 항목]
     * 1. 정렬이 잘 되는가?
     */
    public Page<CommentInfo> getCommentsByPostId(Long postId, PageParam pageParam) {
        Sort sort = Sort.by("commentGroup", "depth", "time");
        Page<CommentInfo> commentInfoList = commentRepository.findByPostId(postId, pageParam.toPageRequest(sort));
        return commentInfoList;
    }

    /**
     * GET /posts/user/{userId}?page={page}&size={size}
     * [repository 테스트 항목]
     * 1. 정렬이 잘 되는가?
     */
    public Page<CommentInfo> getCommentsByUserId(String userId, PageParam pageParam) {
        Sort sort = Sort.by("commentGroup", "depth", "time");
        Page<CommentInfo> commentInfoList = commentRepository.findByUserId(userId, pageParam.toPageRequest(sort));
        return commentInfoList;
    }

    /**
     * PUT /comments/{commentId}
     * [service 테스트 항목]
     * 1. 댓글을 못 찾았을 때 예외가 발생하는가?
     * 2. 데이터가 잘 바뀌는가?
     */
    public CommentInfo changeComment(Long commentId, CommentChange comment) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
        oldComment.setContent(comment.getContent());
        Comment newComment = commentRepository.save(oldComment);
        return ServiceUtil.convertToDest(newComment, CommentInfo.class);
    }

    /**
     * DELETE /comments/{commentId}
     * [service 테스트 항목]
     * 1. 댓글 삭제 중 예외가 발생했을때 false를 반환하는가?
     * 2. 댓글을 못찾으면 예외가 발생하는가?
     * 3. 게시글의 댓글 수는 줄어드는가?
     */
    public boolean removeComment(Long commentId) {
        try {
            Comment comment = commentRepository.findById(commentId).orElseThrow(NotFoundCommentException::new);
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
     * [service 테스트 항목]
     * 1. 게시글을 못 찾았을 때 예외가 발생하는가?
     * 2. 사용자를 못 찾았을 때 예외가 발생하는가?
     * 3. 새로운 댓글 입력시 CommentGroup에 자신의 id가 들어가는가?
     * 4. 새로운 대댓글 입력시 CommentGroup이 그대로 유지되는가?
     * 5. 새로운 댓글 입력시 게시글의 댓글 수가 늘어나는가?
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
        //게시글의 댓글 수 늘림
        post.setCommentCnt(post.getCommentCnt() + 1);
        postRepository.save(post);
        return ServiceUtil.convertToDest(commentRepository.save(newComment), CommentInfo.class);
    }
}
