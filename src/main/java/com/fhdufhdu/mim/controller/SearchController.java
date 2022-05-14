package com.fhdufhdu.mim.controller;

import java.util.List;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.service.SearchService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/movies/{id}")
    public MovieDtoV2 getMovieById(@PathVariable Long id) {
        return searchService.getMovieById(id);
    }

    @GetMapping("/movies/titles")
    public Page<MovieDto> getMovieList(@RequestParam("titles") List<String> titles, int page) {
        return searchService.getMovieList(titles, page);
    }

    @GetMapping("/movies")
    public Page<MovieDto> getMovieList(@RequestParam("title") String title, int page) {
        return searchService.getMovieList(title, page);
    }

    @DeleteMapping("/movies/{movieId}")
    public void removeMovie(@PathVariable Long movieId) {
        searchService.removeMovie(movieId);
    }

    @PutMapping("/movies/{movieId}")
    public void changeMovieInfo(@PathVariable Long movieId, @RequestBody MovieDtoV2 movie) {
        searchService.changeMovieInfo(movieId, movie);
    }

    @PostMapping("/movies")
    public void addMovie(@RequestBody MovieDtoV2 movie) {
        searchService.addMovie(movie);
    }

    @GetMapping("/scean")
    public String searchByScan(@RequestParam("input") String input) {
        return "not complete";
    }

    @GetMapping("/line")
    public String lineByScan(@RequestParam("input") String input) {
        return "not complete";
    }
}
