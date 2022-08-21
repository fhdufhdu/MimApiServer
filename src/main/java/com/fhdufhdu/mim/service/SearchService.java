package com.fhdufhdu.mim.service;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import org.springframework.data.domain.Page;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.dto.MovieLineDto;

public interface SearchService {
        /** GET /movies/{movieId} */
        MovieDtoV2 getMovieById(Long movieId);

        /** GET /movies/{movieId}/background */
        InputStream getMovieBackground(Long movieId) throws FileNotFoundException;

        /** GET /movies/{movieId}/posting */
        InputStream getMoviePoster(Long movieId) throws FileNotFoundException;

        /** POST /movies */
        void addMovie(MovieDtoV2 movie);

        /** GET /scean?input={input} */
        List<MovieDto> searchByScean(String input);

        /** GET /line?input={input} */
        List<MovieLineDto> searchByLine(String input);
}
