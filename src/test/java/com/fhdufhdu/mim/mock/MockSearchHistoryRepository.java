package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.SearchHistory;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class MockSearchHistoryRepository {
    private List<SearchHistory> searchHistories;

    public MockSearchHistoryRepository(MockUserRepository userRepository){
        List<Integer> userIndexList = IndexListGenerator.generate(true);

        searchHistories = new ArrayList<>();
        IntStream.range(0, 10).forEach(idx -> searchHistories.add(SearchHistory.builder()
                .user(userRepository.getDataList().get(userIndexList.get(idx)))
                .id((long) idx)
                .search_text("text" + idx)
                .timestamp(Timestamp.valueOf(LocalDateTime.now().minusDays((long) idx)))
                .build()));
    }

    public List<SearchHistory> getSearchHistories() {
        return searchHistories;
    }
}
