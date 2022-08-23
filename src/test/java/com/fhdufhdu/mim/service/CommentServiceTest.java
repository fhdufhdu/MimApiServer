package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.mock.MockCommentRepository;
import com.fhdufhdu.mim.mock.MockPostRepository;
import com.fhdufhdu.mim.mock.MockUserRepository;
import com.fhdufhdu.mim.repository.CommentRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class CommentServiceTest {
    private CommentService commentService;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @BeforeEach
    public void init(){
        userRepository = new MockUserRepository();
        postRepository = new MockPostRepository((MockUserRepository) userRepository);
        commentRepository = new MockCommentRepository((MockPostRepository) postRepository, (MockUserRepository) userRepository);
        commentService = new CommentService(commentRepository, postRepository, userRepository);
    }

    @Test
    void 게시글아이디로댓글가져오기(){
        final String content = "댓글쓰기!!";
        commentService.writeComment(Comment.Writing.builder()
                        .userId("user0")
                        .content(content)
                        .postId(0l)
                .build());
        Page<Comment.Info> test = commentService.getCommentsByPostId(0l, PageParam.builder().page(0).size(10).build());
        assertThat(test.getContent().stream().map(x -> x.getContent())).contains(content);
    }

    @Test
    void 유저아이디로댓글가져오기(){
        commentRepository.save(Comment.builder()
                .time(Timestamp.valueOf(LocalDateTime.now()))
                .id(10l)
                .commentGroup(10l)
                .content("test10")
                .depth(0)
                .post(postRepository.findById(0l).orElseThrow())
                .user(userRepository.findById("user0").orElseThrow())
                .build());
        Page<Comment.Info> test = commentService.getCommentsByUserId("user0", PageParam.builder().page(0).size(10).build());
        assertThat(test.getContent().stream().map(x -> x.getId())).contains(10l);
    }

    @Test
    void 댓글변경하기(){
        final String content = "수정된 댓글입니다";
        Comment.Change comment = Comment.Change.builder()
               .content(content)
               .build();
        Comment.Info info = commentService.changeComment(0l, comment);
        assertThat(info.getContent()).isEqualTo(content);
    }

    @Test
    void 댓글삭제하기(){
        boolean success = commentService.removeComment(0l);
        try{
            commentService.changeComment(0l, Comment.Change.builder().content("").build());
            fail();
        }catch (Exception e){
            log.debug("성공조건 : 에러발생");
        }
        assertThat(success).isEqualTo(true);
    }
}
