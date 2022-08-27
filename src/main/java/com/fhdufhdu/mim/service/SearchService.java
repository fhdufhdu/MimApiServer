package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.movie.MovieInfo;
import com.fhdufhdu.mim.dto.movie.MovieInfoWithLine;
import com.fhdufhdu.mim.dto.searchhistory.SearchHistoryListElem;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.repository.MovieRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final MovieRepository movieRepository;

    /**
     * GET /movies/{movieId}
     */
    MovieInfo getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return ServiceUtil.convertToDest(movie, MovieInfo.class);
    }

    /**
     * GET /movies/{movieId}/background
     */
    InputStream getMovieBackground(Long movieId) throws FileNotFoundException {
        return null;
    }

    /**
     * GET /movies/{movieId}/posting
     */
    InputStream getMoviePoster(Long movieId) throws FileNotFoundException {
        return null;
    }

    /**
     * GET /search/scene?input={input}
     */
    List<MovieInfo> searchByScene(String input) {
        return null;
    }

    /**
     * GET /search/line?input={input}
     */
    List<MovieInfoWithLine> searchByLine(String input) {
        return null;
    }

    /**
     * GET /search/history/user/{userId}
     */
    Page<SearchHistoryListElem> getSearchHistoryOfUser(String userId, PageParam pageParam) {
        return null;
    }
}
