package com.fhdufhdu.mim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import com.fhdufhdu.mim.dto.PostSearchType;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.Role;

@DataJpaTest
@ActiveProfiles("test")
public class PostRepositoryTest {
        private final PasswordEncoder encoder = new BCryptPasswordEncoder();
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

        private List<Post> getPosts() {
                List<Post> postList = new ArrayList<>();
                Member member = getMember();
                IntStream.range(0, 10).forEach(idx -> postList.add(Post.builder()
                                .commentCnt(0)
                                .time(Timestamp.valueOf(LocalDateTime.now()))
                                .content("content" + idx)
                                .title("title" + idx)
                                .member(member)
                                .build()));
                return postRepository.saveAll(postList);
        }

        @Test
        void 게시글목록가져오기() {
                List<Long> idList = getPosts().stream().map(x -> x.getId())
                                .sorted(Comparator.comparing(Long::longValue).reversed()).collect(Collectors.toList());

                Page<PostListElem> result = postRepository.findByQuery(null, null,
                                PageRequest.of(0, 10, Sort.by("id").descending()));
                assertThat(result.getContent().stream().map(r -> r.getId())).containsSequence(idList);
        }

        @Test
        void 쿼리로게시글목록가져오기() {
                List<Post> postList = getPosts();
                int titleIdx = 1;
                int contentIdx = 2;
                int titleContentIdx = 3;
                int memberIdx = 4;

                Page<PostListElem> resultTitle = postRepository.findByQuery(postList.get(titleIdx).getTitle(),
                                PostSearchType.TITLE, PageRequest.of(0, 10, Sort.by("id").descending()));
                resultTitle.stream().forEach(t -> assertThat(t.getTitle()).contains(postList.get(titleIdx).getTitle()));

                Page<PostListElem> resultContent = postRepository.findByQuery(postList.get(contentIdx).getContent(),
                                PostSearchType.CONTENT, PageRequest.of(0, 10, Sort.by("id").descending()));
                resultContent.stream()
                                .forEach(t -> assertThat(t.getTitle()).contains(postList.get(contentIdx).getTitle()));

                Page<PostListElem> resultTitleContent1 = postRepository.findByQuery(
                                postList.get(titleContentIdx).getTitle(),
                                PostSearchType.TITLE_CONTENT, PageRequest.of(0, 10, Sort.by("id").descending()));
                resultTitleContent1.stream()
                                .forEach(t -> assertThat(t.getTitle())
                                                .contains(postList.get(titleContentIdx).getTitle()));

                Page<PostListElem> resultTitleContent2 = postRepository.findByQuery(
                                postList.get(titleContentIdx).getContent(),
                                PostSearchType.TITLE_CONTENT, PageRequest.of(0, 10, Sort.by("id").descending()));
                resultTitleContent2.stream()
                                .forEach(t -> assertThat(t.getTitle())
                                                .contains(postList.get(titleContentIdx).getTitle()));

                Page<PostListElem> resultMember = postRepository.findByQuery(
                                postList.get(memberIdx).getMember().getId(),
                                PostSearchType.MEMBER, PageRequest.of(0, 10, Sort.by("id").descending()));
                resultMember.stream()
                                .forEach(t -> assertThat(t.getMemberId())
                                                .contains(postList.get(memberIdx).getMember().getId()));
        }
}
