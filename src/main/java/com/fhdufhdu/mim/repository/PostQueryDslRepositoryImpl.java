package com.fhdufhdu.mim.repository;

import static com.fhdufhdu.mim.entity.QPost.post;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.fhdufhdu.mim.dto.PostSearchType;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.service.util.ServiceUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class PostQueryDslRepositoryImpl implements PostQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public PostQueryDslRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public Page<PostListElem> findByQuery(String query, PostSearchType type, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        switch (type) {
            case TITLE:
                builder.and(post.title.contains(query));
                break;
            case CONTENT:
                builder.and(post.content.contains(query));
                break;
            case TITLE_CONTENT:
                builder.and(post.title.contains(query)).or(post.content.contains(query));
                break;
            case MEMBER:
                builder.and(post.member.id.contains(query));
                break;
        }

        List<Post> posts = queryFactory.select(post)
                .from(post)
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(sort(pageable))
                .fetch();

        return new PageImpl<>(ServiceUtil.convertToDest(posts, PostListElem.class), pageable, posts.size());
    }

    private OrderSpecifier<?> sort(Pageable page) {
        // 서비스에서 보내준 Pageable 객체에 정렬조건 null 값 체크
        if (!page.getSort().isEmpty()) {
            // 정렬값이 들어 있으면 for 사용하여 값을 가져온다
            for (Sort.Order order : page.getSort()) {
                // 서비스에서 넣어준 DESC or ASC 를 가져온다.
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                // 서비스에서 넣어준 정렬 조건을 스위치 케이스 문을 활용하여 셋팅하여 준다.
                switch (order.getProperty()) {
                    case "id":
                        return new OrderSpecifier(direction, post.id);
                }
            }
        }
        return null;
    }
}
