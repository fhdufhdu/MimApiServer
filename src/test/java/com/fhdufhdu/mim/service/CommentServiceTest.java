package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.NotFoundCommentException;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;

    @Test
    void 댓글변경_댓글못찾음(){
        Long id = 0l;
        String content = "change content";
        Comment.Change comment = Comment.Change.builder()
                .content(content)
                .build();

        when(commentRepository.findById(any())).then(ivc -> Optional.ofNullable(null));

        try{
            commentService.changeComment(id, comment);
            fail();
        }catch (NotFoundCommentException e){
            log.info(e.toString());
            log.info("예외발생 - 정상");
        }
    }

    @Test
    void 댓글변경(){
        Long id = 0l;
        String oldContent = "old content";
        String content = "change content";
        Comment.Change comment = Comment.Change.builder()
                .content(content)
                .build();
        when(commentRepository.findById(any(Long.class))).then(ivc -> Optional.ofNullable(Comment.builder()
                        .id(ivc.getArgument(0))
                        .content(oldContent)
                .build()));
        when(commentRepository.save(any())).then(ivc -> ivc.getArgument(0));

        Comment.Info result = commentService.changeComment(id, comment);

        assertThat(result.getContent()).isEqualTo(content);
    }

    @Test
    void 댓글삭제_예외발생(){
        Long id = 0l;
        doThrow(new RuntimeException("테스트 예외 발생(삭제 이상)")).when(commentRepository).deleteById(any());
        boolean success = commentService.removeComment(id);
        assertThat(success).isEqualTo(false);
    }

    @Test
    void 댓글추가_새로운댓글(){
        final String content = "새로운 댓글";
        final Long newId = 0l;
        Comment.Writing newComment = Comment.Writing.builder()
                .content(content)
                .build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().build()));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(User.builder().build()));
        when(commentRepository.save(any())).then(ivc -> {
            Comment comment = ivc.getArgument(0);
            if(comment.getId() == null) comment.setId(newId);
            return comment;
        });

        Comment.Info result = commentService.writeComment(newComment);

        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getCommentGroup()).isEqualTo(newId);
    }

    @Test
    void 댓글추가_새로운대댓글(){
        final String content = "새로운 대댓글";
        final Long newId = 1l;
        final Long commentGroup = 0l;
        Comment.Writing newComment = Comment.Writing.builder()
                .content(content)
                .commentGroup(commentGroup)
                .build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().build()));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(User.builder().build()));
        when(commentRepository.save(any())).then(ivc -> {
            Comment comment = ivc.getArgument(0);
            if(comment.getId() == null) comment.setId(newId);
            return comment;
        });

        Comment.Info result = commentService.writeComment(newComment);

        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getCommentGroup()).isEqualTo(commentGroup);
    }

    @Test
    void 댓글추가_게시글못찾음(){
        final String content = "새로운 댓글";
        Comment.Writing newComment = Comment.Writing.builder()
                .content(content)
                .build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        try{
            commentService.writeComment(newComment) ;
            fail();
        }catch (NotFoundPostException e){
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

    @Test
    void 댓글추가_사용자못찾음(){
        final String content = "새로운 댓글";
        Comment.Writing newComment = Comment.Writing.builder()
                .content(content)
                .build();
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().build()));
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        try{
            commentService.writeComment(newComment) ;
            fail();
        }catch (NotFoundUserException e){
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }
}
