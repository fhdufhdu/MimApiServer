package com.fhdufhdu.mim.service;

import java.util.Arrays;
import java.util.List;

import com.fhdufhdu.mim.dto.FeatureDto;
import com.fhdufhdu.mim.dto.GenreDto;
import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieRatingDto;
import com.fhdufhdu.mim.dto.worker.WorkerDto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
public class SearchServiceTest {
    @Autowired
    private SearchService searchService;

    @Test
    public void saveData() {
        // MovieRating movieRating1 = MovieRating.builder().rating("전체관람가").build();
        // MovieRating movieRating2 = MovieRating.builder().rating("12세 이상
        // 관람가").build();
        // MovieRating movieRating3 = MovieRating.builder().rating("15세 이상
        // 관람가").build();
        // MovieRating movieRating4 = MovieRating.builder().rating("청소년 관람 불가").build();

        MovieDto movie1 = MovieDto.builder().title("movie1").build();
        MovieDto movie2 = MovieDto.builder().title("movie2").build();
        MovieDto movie3 = MovieDto.builder().title("movie3").build();
        MovieDto movie4 = MovieDto.builder().title("movie4").build();

        searchService.addMovie(movie1, Arrays.asList("director1"), Arrays.asList("actor1", "actor2", "actor3"),
                Arrays.asList("writer1", "writer2"), Arrays.asList("genre1", "genre2"),
                Arrays.asList("feature1", "feature2"), "전체관람가");

        searchService.addMovie(movie2, Arrays.asList("director2"), Arrays.asList("actor2", "actor3", "actor4"),
                Arrays.asList("writer2", "writer3"), Arrays.asList("genre2", "genre3"),
                Arrays.asList("feature2", "feature3"), "12세 이상 관람가");

        searchService.addMovie(movie3, Arrays.asList("director3"), Arrays.asList("actor3", "actor4", "actor5"),
                Arrays.asList("writer1", "writer4"), Arrays.asList("genre3", "genre4"),
                Arrays.asList("feature1", "feature3"), "15세 이상 관람가");

        searchService.addMovie(movie4, Arrays.asList("director4"), Arrays.asList("actor5", "actor1", "actor6"),
                Arrays.asList("writer4", "writer1"), Arrays.asList("genre4", "genre1"),
                Arrays.asList("feature5", "feature4"), "청소년 관람 불가");

        List<MovieDto> findDtos = searchService.getMovieList(Arrays.asList("movie1"));
        List<WorkerDto> directors = searchService.getDirectorByMovieId(findDtos.get(0).getId());
        List<WorkerDto> actors = searchService.getActorByMovieId(findDtos.get(0).getId());
        List<WorkerDto> writers = searchService.getWriterByMovieId(findDtos.get(0).getId());
        List<GenreDto> genres = searchService.getGenreByMovieId(findDtos.get(0).getId());
        List<FeatureDto> featureDtos = searchService.getFeatureByMovieId(findDtos.get(0).getId());
        MovieRatingDto movieRatingDto = searchService.getMovieRatingByMovieId(findDtos.get(0).getId());
    }
}
