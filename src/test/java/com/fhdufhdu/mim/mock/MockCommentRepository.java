package com.fhdufhdu.mim.mock;


import com.fhdufhdu.mim.entity.Comment;
import com.fhdufhdu.mim.repository.CommentRepository;
import io.swagger.v3.oas.models.media.IntegerSchema;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MockCommentRepository extends MockJapRepository<Comment, Long> implements CommentRepository {
    public MockCommentRepository(MockPostRepository postRepository, MockUserRepository userRepository){
        List<Integer> postIndexList = IndexListGenerator.generate(true);
        List<Integer> userIndexList = IndexListGenerator.generate(true);

        List<Comment> comments = new ArrayList<>();
        IntStream.range(0, 10).forEach(idx -> comments.add(Comment.builder()
                        .user(userRepository.getDataList().get(userIndexList.get(idx)))
                        .post(postRepository.getDataList().get(postIndexList.get(idx)))
                        .depth(0)
                        .commentGroup((long) idx)
                        .content("comment content " + idx)
                        .id((long) idx)
                        .time(Timestamp.valueOf(LocalDateTime.now().minusDays(idx)))
                .build()));

        super.setData(comments);
    }


    @Override
    public Page<Comment.Info> findByPostId(Long postId, Pageable pageable) {
        List<Comment> comments = getDataList().stream().filter(c -> c.getPost().getId().equals(postId)).collect(Collectors.toList());
        Page<Comment> commentPage = MockUtil.convertListToPage(comments, pageable);
        return MockUtil.convertToDest(commentPage, Comment.Info.class);
    }

    @Override
    public Page<Comment.Info> findByUserId(String userId, Pageable pageable) {
        List<Comment> comments = getDataList().stream().filter(c -> c.getUser().getId().equals(userId)).collect(Collectors.toList());
        Page<Comment> commentPage = MockUtil.convertListToPage(comments, pageable);
        return MockUtil.convertToDest(commentPage, Comment.Info.class);
    }
}
