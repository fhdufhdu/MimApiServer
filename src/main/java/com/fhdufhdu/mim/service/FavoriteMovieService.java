package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.favoritemovie.FavoriteMovieAddDto;

import org.springframework.data.domain.Page;

public interface FavoriteMovieService {
    /** GET /favorite-movies/user/{userId} */
    Page<MovieDto> getMovieListByUserId(String userId, int page);

    /** POST /favorite-movies */
    void addFavorite(FavoriteMovieAddDto favoriteMovieDto);

    /** DELETE /favorite-movies/{id} */
    void removeFavorite(Long id);
}
