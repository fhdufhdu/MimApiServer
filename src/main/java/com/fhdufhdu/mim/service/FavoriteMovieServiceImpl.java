package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.favoritemovie.FavoriteMovieAddDto;
import com.fhdufhdu.mim.entity.FavoriteMovie;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.DuplicateFavoriteMovieException;
import com.fhdufhdu.mim.exception.MismatchAuthorException;
import com.fhdufhdu.mim.exception.NotFoundFavoriteMovieException;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.FavoriteMovieRepository;
import com.fhdufhdu.mim.repository.MovieRepository;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FavoriteMovieServiceImpl extends UtilService implements FavoriteMovieService {
    private final FavoriteMovieRepository favoriteMovieRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    @Override
    public Page<MovieDto> getMovieListByUserId(String userId, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Movie> movies = favoriteMovieRepository.findByUserId(userId, pageRequest)
                .map(favorite -> favorite.getMovie());
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public boolean isFavoriteMovie(String userId, Long movieId) {
        if (!userId.equals(getUserId()) && !hasAuthority(Role.ADMIN)) {
            throw new MismatchAuthorException();
        }
        return favoriteMovieRepository.existsByUserIdAndMovieId(userId, movieId);
    }

    @Override
    public void addFavorite(FavoriteMovieAddDto favoriteMovieDto) {
        Movie movie = movieRepository.findById(favoriteMovieDto.getMovieId()).orElseThrow(NotFoundMovieException::new);
        User user = userRepository.findById(favoriteMovieDto.getUserId()).orElseThrow(NotFoundUserException::new);

        if (!user.getId().equals(getUserId()) && !hasAuthority(Role.ADMIN)) {
            throw new MismatchAuthorException();
        }

        if (favoriteMovieRepository.existsByUserIdAndMovieId(favoriteMovieDto.getUserId(),
                favoriteMovieDto.getMovieId())) {
            throw new DuplicateFavoriteMovieException();
        }

        FavoriteMovie newFavoriteMovie = FavoriteMovie.builder()
                .movie(movie)
                .user(user)
                .time(getNowTimestamp())
                .build();
        favoriteMovieRepository.save(newFavoriteMovie);
    }

    @Override
    public void removeFavorite(Long id) {
        FavoriteMovie favoriteMovie = favoriteMovieRepository.findById(id)
                .orElseThrow(NotFoundFavoriteMovieException::new);
        if (!favoriteMovie.getUser().getId().equals(getUserId()) && !hasAuthority(Role.ADMIN)) {
            throw new MismatchAuthorException();
        }
        favoriteMovieRepository.deleteById(id);
    }

    @Override
    public void removeFavorite(String userId, Long movieId) {
        favoriteMovieRepository.deleteByUserIdAndMovieId(userId, movieId);
    }
}
