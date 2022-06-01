package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.favoritemovie.FavoriteMovieAddDto;

import org.springframework.data.domain.Page;

public interface FavoriteMovieService {
    /** GET /favorite-movies/user/{userId} */
    Page<MovieDto> getMovieListByUserId(String userId, int page, int size);

    /** GET /favorite-movies/user/{userId}/movie/{movieId} */
    boolean isFavoriteMovie(String userId, Long movieId);

    /** POST /favorite-movies */
    void addFavorite(FavoriteMovieAddDto favoriteMovieDto);

    /** DELETE /favorite-movies/{id} */
    void removeFavorite(Long id);

    /** DELETE /favorite-movies/user/{userId}/movie/{movieId} */
    void removeFavorite(String userId, Long movieId);
}
