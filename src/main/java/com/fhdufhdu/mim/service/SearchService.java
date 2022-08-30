package com.fhdufhdu.mim.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.movie.MovieInfo;
import com.fhdufhdu.mim.dto.movie.MovieInfoWithLine;
import com.fhdufhdu.mim.dto.search.SearchResult;
import com.fhdufhdu.mim.dto.search.SearchResultElem;
import com.fhdufhdu.mim.dto.search.SearchType;
import com.fhdufhdu.mim.dto.searchhistory.SearchHistoryListElem;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.repository.MovieRepository;
import com.fhdufhdu.mim.repository.SearchHistoryRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final MovieRepository movieRepository;
    private final SearchHistoryRepository searchHistoryRepository;
    private final RestTemplate restTemplate;

    @Value("{dlserver.address}")
    private static String dlAddress;
    private static final String DL_SEARCH = "/search";

    @Value("{movie.image}")
    private static String dirPath;

    public static void setDlAddress(String address) {
        dlAddress = address;
    }

    /**
     * GET /movies/{movieId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>영화 못찾을시 예외발생하는지
     * </ol>
     */
    public MovieInfo getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return ServiceUtil.convertToDest(movie, MovieInfo.class);
    }

    /**
     * GET /movies/{movieId}/background
     * 
     */
    public InputStream getMovieBackground(Long movieId) throws FileNotFoundException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return new FileInputStream(dirPath + movie.getDirName() + "/image.png");
    }

    /**
     * GET /movies/{movieId}/posting
     */
    public InputStream getMoviePoster(Long movieId) throws FileNotFoundException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return new FileInputStream(dirPath + movie.getDirName() + "/poster.png");
    }

    /**
     * GET /search/scene?input={input}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>딥러닝 서버랑 통신중에 에러 발생시 DlServerException 예외가 발생하는지
     * <li>objectMapper.readValue 함수 실행 중 에러 발생시 DlServerException 예외가 발생하는지
     * <li>api 결과와 함수결과가 동일한지
     * </ol>
     */
    public List<MovieInfo> searchByScene(String query) {
        Map<String, String> var = new HashMap<>();
        var.put("type", SearchType.SCENE.name());
        var.put("query", query);
        SearchResult searchResult = restTemplate.getForObject(dlAddress + DL_SEARCH + "?type={type}&query={query}",
                SearchResult.class, var);
        List<Long> idList = searchResult.getData().stream().map(SearchResultElem::getId).collect(Collectors.toList());
        return ServiceUtil.convertToDest(movieRepository.findByIdList(idList), MovieInfo.class);
    }

    /**
     * GET /search/line?input={input}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>딥러닝 서버랑 통신중에 에러 발생시 DlServerException 예외가 발생하는지
     * <li>objectMapper.readValue 함수 실행 중 에러 발생시 DlServerException 예외가 발생하는지
     * <li>영화 id와 대사가 제대로 잘 매치되는지
     * <li>api결과와 함수결과가 일치하는지
     * </ol>
     */
    public List<MovieInfoWithLine> searchByLine(String query) {
        Map<String, String> var = new HashMap<>();
        var.put("type", SearchType.LINE.name());
        var.put("query", query);
        SearchResult searchResult = restTemplate.getForObject(dlAddress + DL_SEARCH + "?type={type}&query={query}",
                SearchResult.class, var);
        Map<Long, List<String>> subtitles = new HashMap<>();
        List<Long> idList = new ArrayList<>();
        searchResult.getData().stream().forEach(elem -> {
            subtitles.put(elem.getId(), elem.getSubtitle());
            idList.add(elem.getId());
        });
        List<MovieInfoWithLine> result = ServiceUtil.convertToDest(movieRepository.findByIdList(idList),
                MovieInfoWithLine.class);
        result.stream().forEach(dto -> dto.setLines(subtitles.get(dto.getId())));
        return result;
    }

    /**
     * GET /search/history/user/{userId}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>영화 못찾을시 예외발생하는지
     * </ol>
     */
    public Page<SearchHistoryListElem> getSearchHistoryOfUser(String memberId, PageParam pageParam) {
        return searchHistoryRepository.findByMemberId(memberId,
                pageParam.toPageRequest());
    }
}
