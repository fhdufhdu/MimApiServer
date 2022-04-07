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

        /** GET /movies?titles={title1, title2, title3} */
        List<MovieDto> getMovieList(List<String> titles);

        /** DELETE /movies/{movieId} */
        void removeMovie(Long movieId);

        /** PUT /movies/{movieId} */
        void changeMovieInfo(Long movieId, MovieDto movie, List<WorkerDto> directors, List<WorkerDto> actors,
                        List<WorkerDto> writers,
                        List<GenreDto> genres, List<FeatureDto> features, MovieRatingDto rating);

        /** POST /movies */
        void addMovie(MovieDto movie, List<WorkerDto> directors, List<WorkerDto> actors, List<WorkerDto> writers,
                        List<GenreDto> genres, List<FeatureDto> features, MovieRatingDto rating);

        /** GET /ai-server/scean?input={input} */
        List<MovieDto> searchByScean(String input);

        /** GET /ai-server/line?input={input} */
        List<MovieDto> searchByLine(String input);
}
