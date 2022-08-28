package com.fhdufhdu.mim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.dto.movie.MovieInfo;
import com.fhdufhdu.mim.dto.movie.MovieInfoWithLine;
import com.fhdufhdu.mim.dto.search.SearchResult;
import com.fhdufhdu.mim.dto.search.SearchResultElem;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.SearchHistory;
import com.fhdufhdu.mim.repository.MovieRepository;

import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@SpringBootTest
@Slf4j
public class SearchServiceApiTest {
    @Autowired
    private SearchService searchService;
    @MockBean
    private MovieRepository movieRepository;
    @MockBean
    private SearchHistory searchHistory;
    @SpyBean
    private RestTemplate restTemplate;
    @SpyBean
    private Environment environment;

    private static MockWebServer mockWebServer;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void start() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        objectMapper = new ObjectMapper();
        SearchService.setDlAddress(String.format("http://localhost:%d", mockWebServer.getPort()));
    }

    @AfterAll
    static void close() throws IOException {
        mockWebServer.shutdown();
    }

    private SearchResult getData() {
        List<SearchResultElem> data = new ArrayList<>();
        IntStream.range(0, 10).forEach(idx -> {
            List<String> subtitle = new ArrayList<>();
            IntStream.range(idx, idx + 5).forEach(jdx -> subtitle.add("subtitle" + jdx));
            data.add(SearchResultElem.builder()
                    .id((long) idx)
                    .subtitle(subtitle)
                    .build());
        });
        return SearchResult.builder().data(data).build();
    }

    @Test
    void 장면검색() throws JsonProcessingException {
        SearchResult response = getData();
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        when(movieRepository.findByIdList(any())).then(ivc -> {
            List<Movie> movieList = new ArrayList<>();
            ((List<Long>) ivc.getArgument(0)).stream().forEach(id -> movieList.add(Movie.builder().id(id).build()));
            return movieList;
        });

        List<MovieInfo> result = searchService.searchByScene("test");
        assertThat(result.stream().map(MovieInfo::getId))
                .containsAll(LongStream.range(0, 10).boxed().collect(Collectors.toList()));

    }

    @Test
    void 대사검색() throws JsonProcessingException {
        SearchResult response = getData();
        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(response))
                .addHeader("Content-Type", "application/json"));

        when(movieRepository.findByIdList(any())).then(ivc -> {
            List<Movie> movieList = new ArrayList<>();
            ((List<Long>) ivc.getArgument(0)).stream().forEach(id -> movieList.add(Movie.builder().id(id).build()));
            return movieList;
        });

        List<MovieInfoWithLine> result = searchService.searchByLine("test");
        MovieInfoWithLine result2 = result.get(0);
        assertThat(result.stream().map(MovieInfoWithLine::getId))
                .containsAll(LongStream.range(0, 10).boxed().collect(Collectors.toList()));
        assertThat(result2.getLines()).containsAll(response.getData().get(result2.getId().intValue()).getSubtitle());
    }
}
