package com.fhdufhdu.mim.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;

import org.springframework.data.domain.Page;

public interface SearchService {
        /** GET /movies/{movieId} */
        MovieDtoV2 getMovieById(Long movieId);

        // /** GET /directors/movie/{movieId} */
        // List<WorkerDto> getDirectorByMovieId(Long movieId);

        // /** GET /actors/movie/{movieId} */
        // List<WorkerDto> getActorByMovieId(Long movieId);

        // /** GET /writers/movie/{movieId} */
        // List<WorkerDto> getWriterByMovieId(Long movieId);

        // /** GET /genres/movie/{movieId} */
        // List<GenreDto> getGenreByMovieId(Long movieId);

        // /** GET /features/movie/{movieId} */
        // List<FeatureDto> getFeatureByMovieId(Long movieId);

        // /** GET /ratings/movie/{movieId} */
        // MovieRatingDto getMovieRatingByMovieId(Long movieId);

        /** GET /movies?titles={title1, title2, title3} */
        List<MovieDto> getMovieList(List<String> titles);

        /** GET /movies?ids={id1, id2, id3} */
        List<MovieDto> getMovieListByIds(List<Long> ids);

        /** GET /movies */
        Page<MovieDto> getMovieList(String title, int page, int size);

        /** GET /movies/{movieId}/background */
        InputStream getMovieBackground(Long movieId) throws FileNotFoundException;

        /** GET /movies/{movieId}/posting */
        InputStream getMoviePoster(Long movieId) throws FileNotFoundException;

        /** DELETE /movies/{movieId} */
        void removeMovie(Long movieId);

        /** PUT /movies/{movieId} */
        void changeMovieInfo(Long movieId, MovieDtoV2 movie);

        /** POST /movies */
        void addMovie(MovieDtoV2 movie);

        /** GET /scean?input={input} */
        List<Object> searchByScean(String input);

        /** GET /line?input={input} */
        List<Object> searchByLine(String input);
}
