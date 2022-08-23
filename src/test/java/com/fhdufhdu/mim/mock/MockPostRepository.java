package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class MockPostRepository extends MockJapRepository<Post, Long> implements PostRepository {
    public MockPostRepository(MockUserRepository userRepository){
        List<Integer> indexList = IndexListGenerator.generate(true);

        List<Post> posts = new ArrayList<>();
        IntStream.range(0, 10).forEach(idx -> posts.add(Post.builder()
                        .title("post title " + idx)
                        .time(Timestamp.valueOf(LocalDateTime.now().minusDays(idx)))
                        .content("post content " + idx)
                        .id((long) idx)
                        .commentCnt(0)
                        .user(userRepository.getDataList().get(indexList.get(idx)))
                .build()));

        super.setData(posts);
    }

    @Override
    public Page<Post> findByBoardId(Long boardId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<Post> findByBoardIdAndQuery(Long boardId, String query, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Post> findByBoardIdAndPostingNumber(Long boardId, Long postingNumber) {
        return Optional.empty();
    }

    @Override
    public Page<Post> findByUserId(String userId, Pageable pageable) {
        return null;
    }

    @Override
    public List<Post> findByIds(List<Long> ids) {
        return null;
    }
}
