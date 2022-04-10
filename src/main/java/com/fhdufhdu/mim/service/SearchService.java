package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.FeatureDto;
import com.fhdufhdu.mim.dto.GenreDto;
import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieRatingDto;
import com.fhdufhdu.mim.dto.worker.WorkerDto;

public interface SearchService {
        /** GET /movies/{movieId} */
        MovieDto getMovieById(Long movieId);

        /** GET /directors?movie-id={movieId} */
        List<WorkerDto> getDirectorByMovieId(Long movieId);

        /** GET /actors?movie-id={movieId} */
        List<WorkerDto> getActorByMovieId(Long movieId);

        /** GET /writes?movie-id={movieId} */
        List<WorkerDto> getWriterByMovieId(Long movieId);

        /** GET /genres?movie-id={movieId} */
        List<GenreDto> getGenreByMovieId(Long movieId);

        /** GET /features?movie-id={movieId} */
        List<FeatureDto> getFeatureByMovieId(Long movieId);

        /** GET /ratings?movie-id={movieId} */
        MovieRatingDto getMovieRatingByMovieId(Long movieId);

        /** GET /movies?titles={title1, title2, title3} */
        List<MovieDto> getMovieList(List<String> titles);

        /** DELETE /movies/{movieId} */
        void removeMovie(Long movieId);

        /** PUT /movies/{movieId} */
        void changeMovieInfo(Long movieId, MovieDto movie, List<String> directors, List<String> actors,
                        List<String> writers,
                        List<String> genres, List<String> features, String rating);

        /** POST /movies */
        void addMovie(MovieDto movie, List<String> directors, List<String> actors,
                        List<String> writers,
                        List<String> genres, List<String> features, String rating);

        /** GET /scean?input={input} */
        List<MovieDto> searchByScean(String input);

        /** GET /line?input={input} */
        List<MovieDto> searchByLine(String input);
}
