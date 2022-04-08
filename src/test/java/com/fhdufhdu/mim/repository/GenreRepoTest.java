package com.fhdufhdu.mim.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.dto.GenreDto;
import com.fhdufhdu.mim.entity.Genre;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.MovieGenre;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class GenreRepoTest {
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    MovieGenreRepository mGenreRepository;
    @Autowired
    GenreRepository genreRepository;

    final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void 장르추출테스트() {
        Movie movie1 = new Movie(null, "1", "1", 1, "1", "1", null, "1");
        Movie movie2 = new Movie(null, "2", "1", 1, "1", "1", null, "1");
        Movie movie3 = new Movie(null, "3", "1", 1, "1", "1", null, "1");

        movie1 = movieRepository.save(movie1);
        movie2 = movieRepository.save(movie2);
        movie3 = movieRepository.save(movie3);

        Genre genre1 = new Genre();
        genre1.setId(null);
        genre1.setGenreName("genreName1");
        Genre genre2 = new Genre();
        genre2.setId(null);
        genre2.setGenreName("genreName2");
        Genre genre3 = new Genre();
        genre3.setId(null);
        genre3.setGenreName("genreName3");
        Genre genre4 = new Genre();
        genre4.setId(null);
        genre4.setGenreName("genreName4");

        genre1 = genreRepository.save(genre1);
        genre2 = genreRepository.save(genre2);
        genre3 = genreRepository.save(genre3);
        genre4 = genreRepository.save(genre4);

        MovieGenre movieGenre1 = new MovieGenre(null, movie1, genre1);
        MovieGenre movieGenre2 = new MovieGenre(null, movie1, genre2);
        MovieGenre movieGenre3 = new MovieGenre(null, movie1, genre3);
        MovieGenre movieGenre4 = new MovieGenre(null, movie2, genre2);
        MovieGenre movieGenre5 = new MovieGenre(null, movie2, genre3);
        MovieGenre movieGenre6 = new MovieGenre(null, movie2, genre4);

        movieGenre1 = mGenreRepository.save(movieGenre1);
        movieGenre2 = mGenreRepository.save(movieGenre2);
        movieGenre3 = mGenreRepository.save(movieGenre3);
        movieGenre4 = mGenreRepository.save(movieGenre4);
        movieGenre5 = mGenreRepository.save(movieGenre5);
        movieGenre6 = mGenreRepository.save(movieGenre6);

        List<String> genreNames = new ArrayList<>();
        genreNames.add(genre1.getGenreName());
        genreNames.add(genre2.getGenreName());
        genreNames.add(genre3.getGenreName());

        List<Genre> genres = genreRepository.findByMovieId(movie1.getId());

        List<GenreDto> genreDtos = genres.stream().map(x -> modelMapper.map(x, GenreDto.class))
                .collect(Collectors.toList());

        genres = genreDtos.stream().map(x -> modelMapper.map(x, Genre.class))
                .collect(Collectors.toList());

        for (int i = 0; i < genres.size(); i++) {
            assertThat(genres.get(i).getGenreName()).isEqualTo(genreNames.get(i));
        }
    }
}
