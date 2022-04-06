package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.Feature;
import com.fhdufhdu.mim.entity.Genre;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.MovieRating;
import com.fhdufhdu.mim.entity.worker.Actor;
import com.fhdufhdu.mim.entity.worker.Director;
import com.fhdufhdu.mim.entity.worker.Writer;

public interface SearchService {
    Movie getMovieById(Long movieId);

    List<Movie> getMovieList(List<String> titles);

    void removeMovie(String adminId, Long movieId);

    void changeMovieInfo(Movie movie, List<Director> directors, List<Actor> actors, List<Writer> writers,
            List<Genre> genres, List<Feature> features, MovieRating rating);

    void addMovie(Movie movie, List<Director> directors, List<Actor> actors, List<Writer> writers,
            List<Genre> genres, List<Feature> features, MovieRating rating);

    List<Movie> searchByScean(String input);

    List<Movie> searchByLint(String input);
}
