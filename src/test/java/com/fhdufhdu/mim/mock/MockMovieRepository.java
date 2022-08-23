package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

public class MockMovieRepository {
    private List<Movie> movies;

    public MockMovieRepository(){
        movies = new ArrayList<>();
        LongStream.range(0, 10).forEach(idx -> movies.add(Movie.builder()
                        .actors("1,2,3")
                        .directors("1,2,3")
                        .dirName("title"+idx)
                        .engTitle("title"+idx)
                        .features("1,2,3")
                        .movieRating("전체이용가")
                        .id(idx)
                        .genres("1,2,3")
                        .runningTime("100분")
                        .synopsis("blah blah")
                        .title("title"+idx)
                        .year(2022)
                .build()));
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
