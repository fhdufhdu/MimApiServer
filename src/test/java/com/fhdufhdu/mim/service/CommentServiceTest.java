package com.fhdufhdu.mim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private PostRepository postRepository;

    @Test
    void 댓글변경_댓글못찾음() {
        Long id = 0l;
        String content = "change content";
        CommentChange comment = CommentChange.builder().content(content).build();

        when(commentRepository.findById(any())).then(ivc -> Optional.ofNullable(null));

        try {
            commentService.changeComment(id, comment);
            fail();
        } catch (NotFoundCommentException e) {
            log.info(e.toString());
            log.info("예외발생 - 정상");
        }
    }

    @Test
    void 댓글변경() {
        Long id = 0l;
        String oldContent = "old content";
        String content = "change content";
        CommentChange comment = CommentChange.builder().content(content).build();
        when(commentRepository.findById(any(Long.class)))
                .then(ivc -> Optional.ofNullable(Comment.builder().id(ivc.getArgument(0)).content(oldContent).build()));
        when(commentRepository.save(any())).then(ivc -> ivc.getArgument(0));

        CommentInfo result = commentService.changeComment(id, comment);

        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    void 댓글삭제_예외발생() {
        Long id = 0l;
        int commentCnt = 10;
        Post post = Post.builder().commentCnt(commentCnt).build();
        when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(Comment.builder()
                .post(post)
                .build()));
        when(postRepository.save(any())).then(ivc -> ivc.getArgument(0));
        doThrow(new RuntimeException("테스트 예외 발생(삭제 이상)")).when(commentRepository).delete(any());
        boolean success = commentService.removeComment(id);
        assertThat(success).isFalse();
    }

    @Test
    void 댓글삭제_댓글못찾음() {
        Long id = 0l;
        when(commentRepository.findById(id)).thenReturn(null);
        boolean success = commentService.removeComment(id);
        assertThat(success).isFalse();
    }

    @Test
    void 댓글삭제_게시글댓글수감소() {
        Long id = 0l;
        int commentCnt = 10;
        Post post = Post.builder().commentCnt(commentCnt).build();
        when(commentRepository.findById(id)).thenReturn(Optional.ofNullable(Comment.builder()
                .post(post)
                .build()));
        when(postRepository.save(any())).then(ivc -> ivc.getArgument(0));
        boolean success = commentService.removeComment(id);
        assertThat(success).isEqualTo(true);
        assertThat(post.getCommentCnt()).isEqualTo(commentCnt - 1);
    }

    @Test
    void 댓글추가_새로운댓글() {
        final String content = "새로운 댓글";
        final Long newId = 0l;
        CommentWriting newComment = CommentWriting.builder().content(content).build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().commentCnt(0).build()));
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder().build()));
        when(commentRepository.save(any())).then(ivc -> {
            Comment comment = ivc.getArgument(0);
            if (comment.getId() == null)
                comment.setId(newId);
            return comment;
        });

        CommentInfo result = commentService.writeComment(newComment);

        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getCommentGroup()).isEqualTo(newId);
    }

    @Test
    void 댓글추가_새로운대댓글() {
        final String content = "새로운 대댓글";
        final Long newId = 1l;
        final Long commentGroup = 0l;
        CommentWriting newComment = CommentWriting.builder().content(content).commentGroup(commentGroup).build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().commentCnt(0).build()));
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder().build()));
        when(commentRepository.save(any())).then(ivc -> {
            Comment comment = ivc.getArgument(0);
            if (comment.getId() == null)
                comment.setId(newId);
            return comment;
        });

        CommentInfo result = commentService.writeComment(newComment);

        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getCommentGroup()).isEqualTo(commentGroup);
    }

    @Test
    void 댓글추가_게시글못찾음() {
        final String content = "새로운 댓글";
        CommentWriting newComment = CommentWriting.builder().content(content).build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        try {
            commentService.writeComment(newComment);
            fail();
        } catch (NotFoundPostException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

    @Test
    void 댓글추가_사용자못찾음() {
        final String content = "새로운 댓글";
        CommentWriting newComment = CommentWriting.builder().content(content).build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().build()));
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        try {
            commentService.writeComment(newComment);
            fail();
        } catch (NotFoundMemberException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

    @Test
    void 댓글추가_게시글댓글수증가() {
        final String content = "새로운 댓글";
        final Long newId = 0l;
        final int commentCnt = 10;
        Post post = Post.builder().commentCnt(commentCnt).build();
        CommentWriting newComment = CommentWriting.builder().content(content).build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(post));
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder().build()));
        when(commentRepository.save(any())).then(ivc -> {
            Comment comment = ivc.getArgument(0);
            if (comment.getId() == null)
                comment.setId(newId);
            return comment;
        });

        CommentInfo result = commentService.writeComment(newComment);

        assertThat(post.getCommentCnt()).isEqualTo(commentCnt + 1);
    }
}
