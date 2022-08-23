package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.SearchHistory;
import org.springframework.data.domain.Page;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public interface SearchService {
        /** GET /movies/{movieId} */
        Movie.Info getMovieById(Long movieId);

        /** GET /movies/{movieId}/background */
        InputStream getMovieBackground(Long movieId) throws FileNotFoundException;

        /** GET /movies/{movieId}/posting */
        InputStream getMoviePoster(Long movieId) throws FileNotFoundException;

        /** GET /search/scene?input={input} */
        List<Movie.Info> searchByScene(String input);

        /** GET /search/line?input={input} */
        List<Movie.InfoWithLine> searchByLine(String input);

        /** GET /search/history/user/{userId} */
        Page<SearchHistory.ListElem> getSearchHistoryOfUser(String userId, PageParam pageParam);
}
