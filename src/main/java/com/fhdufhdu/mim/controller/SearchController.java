package com.fhdufhdu.mim.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.movie.MovieInfo;
import com.fhdufhdu.mim.dto.movie.MovieInfoWithLine;
import com.fhdufhdu.mim.dto.searchhistory.SearchHistoryListElem;
import com.fhdufhdu.mim.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/movies/{movieId}")
    public MovieInfo getMovieById(@PathVariable("movidId") Long movieId) {
        return searchService.getMovieById(movieId);
    }

    @GetMapping("/movies/{movieId}/background")
    public void getMovieBackground(HttpServletResponse response, @PathVariable("movieId") Long movieId)
            throws IOException {
        InputStream in = searchService.getMovieBackground(movieId);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/movies/{movieId}/poster")
    public void getMoviePoster(HttpServletResponse response, @PathVariable("movieId") Long movieId)
            throws IOException {
        InputStream in = searchService.getMoviePoster(movieId);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/search/scene")
    public List<MovieInfo> searchByScene(@RequestParam("query") String query, @RequestParam("member") String memberId) {
        return searchService.searchByScene(query, memberId);
    }

    @GetMapping("/search/line")
    public List<MovieInfoWithLine> searchByLine(@RequestParam("query") String query,
            @RequestParam("member") String memberId) {
        return searchService.searchByLine(query, memberId);
    }

    @GetMapping("/search/history/members/{memberId}")
    public Page<SearchHistoryListElem> getSearchHistroyOfUser(@PathVariable("memberId") String memberId,
            @ModelAttribute PageParam pageParam) {
        return searchService.getSearchHistoryOfUser(memberId, pageParam);
    }
}
