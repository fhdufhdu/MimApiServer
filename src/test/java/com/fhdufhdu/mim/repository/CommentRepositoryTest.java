package com.fhdufhdu.mim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.fhdufhdu.mim.dto.comment.CommentInfo;
import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.Role;

@DataJpaTest
@ActiveProfiles("test")
public class CommentRepositoryTest {
        private final PasswordEncoder encoder = new BCryptPasswordEncoder();
        @Autowired
        private CommentRepository commentRepository;
        @Autowired
        private PostRepository postRepository;
        @Autowired
        private MemberRepository memberRepository;

        private Member getMember() {
                return memberRepository.save(Member.builder()
                                .pw(encoder.encode("password"))
                                .role(Role.MEMBER)
                                .nickname("nickname")
                                .id("userid")
                                .build());
        }

        private Post getPost(Member member) {
                return postRepository.save(Post.builder()
                                .title("title")
                                .commentCnt(0)
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .content("content")
                                .member(member)
                                .build());
        }

        @Test
        void 댓글대댓글정렬() {
                Member member = getMember();
                Post post = getPost(member);
                Comment comment1 = commentRepository.save(Comment.builder()
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .member(member)
                                .content("content1")
                                .post(post)
                                .depth(0)
                                .build());
                Comment comment2 = commentRepository.save(Comment.builder()
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .member(member)
                                .content("content2")
                                .post(post)
                                .depth(0)
                                .build());
                comment1.setCommentGroup(comment1.getId());
                comment2.setCommentGroup(comment2.getId());
                comment1 = commentRepository.saveAndFlush(comment1);
                comment2 = commentRepository.saveAndFlush(comment2);
                Comment comment1_sub1 = commentRepository.save(Comment.builder()
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .member(member)
                                .content("content1_sub2")
                                .post(post)
                                .depth(1)
                                .commentGroup(comment1.getId())
                                .build());
                Comment comment2_sub1 = commentRepository.save(Comment.builder()
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .member(member)
                                .content("content2_sub1")
                                .post(post)
                                .depth(1)
                                .commentGroup(comment2.getId())
                                .build());
                Comment comment1_sub2 = commentRepository.save(Comment.builder()
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .member(member)
                                .content("content1_sub2")
                                .post(post)
                                .depth(1)
                                .commentGroup(comment1.getId())
                                .build());

                Sort sort = Sort.by("commentGroup", "depth", "time");
                Page<CommentInfo> result = commentRepository.findByPostId(post.getId(), PageRequest.of(0, 10, sort));
                assertThat(result.getContent().stream().map(r -> r.getId())).containsSequence(comment1.getId(),
                                comment1_sub1.getId(), comment1_sub2.getId(), comment2.getId(), comment2_sub1.getId());
        }
}
