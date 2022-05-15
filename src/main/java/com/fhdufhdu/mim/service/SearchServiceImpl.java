package com.fhdufhdu.mim.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.entity.Feature;
import com.fhdufhdu.mim.entity.Genre;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.MovieFeature;
import com.fhdufhdu.mim.entity.MovieGenre;
import com.fhdufhdu.mim.entity.MovieRating;
import com.fhdufhdu.mim.entity.MovieWorker;
import com.fhdufhdu.mim.entity.worker.Actor;
import com.fhdufhdu.mim.entity.worker.Director;
import com.fhdufhdu.mim.entity.worker.Writer;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.repository.ActorRepository;
import com.fhdufhdu.mim.repository.DirectorRepository;
import com.fhdufhdu.mim.repository.FeatureRepository;
import com.fhdufhdu.mim.repository.GenreRepository;
import com.fhdufhdu.mim.repository.MovieFeatureRepository;
import com.fhdufhdu.mim.repository.MovieGenreRepository;
import com.fhdufhdu.mim.repository.MovieRatingRepository;
import com.fhdufhdu.mim.repository.MovieRepository;
import com.fhdufhdu.mim.repository.MovieWorkerRepository;
import com.fhdufhdu.mim.repository.WriterRepository;
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
public class SearchServiceImpl extends UtilService implements SearchService {
    private static final int PAGE_SIZE = 10;
    private final MovieRepository movieRepository;
    private final MovieGenreRepository movieGenreRepository;
    private final MovieFeatureRepository movieFeatureRepository;
    private final MovieWorkerRepository movieWorkerRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final DirectorRepository directorRepository;
    private final ActorRepository actorRepository;
    private final WriterRepository writerRepository;
    private final GenreRepository genreRepository;
    private final FeatureRepository featureRepository;

    @Override
    public MovieDtoV2 getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        MovieDto movieDto = convertToDest(movie, MovieDto.class);
        MovieDtoV2 movieDtoV2 = MovieDtoV2.builder()
                .movieDto(movieDto)
                .directors(getDirectorByMovieId(movieId))
                .actors(getActorByMovieId(movieId))
                .writers(getWriterByMovieId(movieId))
                .genres(getGenreByMovieId(movieId))
                .features(getFeatureByMovieId(movieId))
                .rating(getMovieRatingByMovieId(movieId))
                .build();

        return movieDtoV2;
    }

    private List<String> getDirectorByMovieId(Long movieId) {
        List<String> directors = directorRepository.findByMovieId(movieId).stream().map(worker -> worker.getName())
                .collect(Collectors.toList());
        return directors;
    }

    private List<String> getActorByMovieId(Long movieId) {
        List<String> actors = actorRepository.findByMovieId(movieId).stream().map(worker -> worker.getName())
                .collect(Collectors.toList());
        return actors;
    }

    private List<String> getWriterByMovieId(Long movieId) {
        List<String> writers = writerRepository.findByMovieId(movieId).stream().map(worker -> worker.getName())
                .collect(Collectors.toList());
        return writers;
    }

    private List<String> getGenreByMovieId(Long movieId) {
        List<String> genres = genreRepository.findByMovieId(movieId).stream().map(genre -> genre.getGenreName())
                .collect(Collectors.toList());
        return genres;
    }

    private List<String> getFeatureByMovieId(Long movieId) {
        List<String> features = featureRepository.findByMovieId(movieId).stream()
                .map(feature -> feature.getFeatureName())
                .collect(Collectors.toList());
        return features;
    }

    private String getMovieRatingByMovieId(Long movieId) {
        MovieRating rating = movieRatingRepository.findByMovieId(movieId).get();
        return rating.getRating();
    }

    @Override
    public Page<MovieDto> getMovieList(List<String> titles, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<Movie> movies = movieRepository.findByTitleList(titles, pageRequest);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public Page<MovieDto> getMovieList(String title, int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        PageRequest pageRequest = PageRequest.of(page, PAGE_SIZE, sort);
        Page<Movie> movies = movieRepository.findByTitle(title, pageRequest);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public void removeMovie(Long movieId) {
        // if (!hasPermission(Role.ADMIN)) {
        // throw new WrongPermissionException();
        // }
        // movieRepository.deleteById(movieId);
        // 구현하지 않는 것이 더 좋아보임
    }

    @Override
    public void changeMovieInfo(Long movieId, MovieDtoV2 movie) {

        // if (!hasPermission(Role.ADMIN)) {
        // throw new WrongPermissionException();
        // }
        Movie originalMovie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        MovieRating movieRating = movieRatingRepository.findByRating(movie.getRating()).get();
        originalMovie.setTitle(movie.getMovieDto().getTitle());
        originalMovie.setEngTitle(movie.getMovieDto().getEngTitle());
        originalMovie.setYear(movie.getMovieDto().getYear());
        originalMovie.setSynopsis(movie.getMovieDto().getSynopsis());
        originalMovie.setRunningTime(movie.getMovieDto().getRunningTime());
        originalMovie.setDirName(movie.getMovieDto().getDirName());
        originalMovie.setMovieRating(movieRating);

        movieWorkerRepository.deleteByMovieId(movieId);
        movieGenreRepository.deleteByMovieId(movieId);
        movieFeatureRepository.deleteByMovieId(movieId);

        for (String director : movie.getDirectors()) {
            Director ds = directorRepository.findByName(director).orElse(null);
            if (ds == null) {
                ds = Director.builder().name(director).build();
                ds = directorRepository.save(ds);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(ds).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String actor : movie.getActors()) {

            Actor ac = actorRepository.findByName(actor).orElse(null);
            if (ac == null) {
                ac = Actor.builder().name(actor).build();
                ac = actorRepository.save(ac);

            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(ac).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String writer : movie.getWriters()) {
            Writer wt = writerRepository.findByName(writer).orElse(null);
            if (wt == null) {
                wt = Writer.builder().name(writer).build();
                wt = writerRepository.save(wt);

            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(wt).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String genre : movie.getGenres()) {
            Genre gr = genreRepository.findByGenreName(genre).orElse(null);
            if (gr == null) {
                gr = Genre.builder().genreName(genre).build();
                gr = genreRepository.save(gr);

            }
            MovieGenre movieGenre = MovieGenre.builder().movie(originalMovie).genre(gr).build();
            movieGenreRepository.save(movieGenre);
        }

        for (String feature : movie.getFeatures()) {
            Feature ft = featureRepository.findByFeatureName(feature).orElse(null);
            if (ft == null) {
                ft = Feature.builder().featureName(feature).build();
                ft = featureRepository.save(ft);

            }
            MovieFeature movieFeature = MovieFeature.builder().movie(originalMovie).feature(ft).build();
            movieFeatureRepository.save(movieFeature);
        }

        movieRepository.save(originalMovie);
    }

    @Override
    public void addMovie(MovieDtoV2 movie) {
        MovieRating movieRating = movieRatingRepository.findByRating(movie.getRating()).get();
        Movie newMovie = Movie.builder()
                .title(movie.getMovieDto().getTitle())
                .engTitle(movie.getMovieDto().getEngTitle())
                .year(movie.getMovieDto().getYear())
                .synopsis(movie.getMovieDto().getSynopsis())
                .runningTime(movie.getMovieDto().getRunningTime())
                .movieRating(movieRating)
                .dirName(movie.getMovieDto().getDirName())
                .build();

        newMovie = movieRepository.save(newMovie);

        for (String director : movie.getDirectors()) {
            Director ds = directorRepository.findByName(director).orElse(null);
            if (ds == null) {
                ds = Director.builder().name(director).build();
                ds = directorRepository.save(ds);

            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(ds).build();
            movieWorkerRepository.save(movieWorker);

        }

        for (String actor : movie.getActors()) {
            Actor ac = actorRepository.findByName(actor).orElse(null);
            if (ac == null) {
                ac = Actor.builder().name(actor).build();
                ac = actorRepository.save(ac);

            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(ac).build();
            movieWorkerRepository.save(movieWorker);

        }

        for (String writer : movie.getWriters()) {
            Writer wt = writerRepository.findByName(writer).orElse(null);
            if (wt == null) {
                wt = Writer.builder().name(writer).build();
                wt = writerRepository.save(wt);

            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(wt).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String genre : movie.getGenres()) {
            Genre gr = genreRepository.findByGenreName(genre).orElse(null);
            if (gr == null) {
                gr = Genre.builder().genreName(genre).build();
                gr = genreRepository.save(gr);

            }
            MovieGenre movieGenre = MovieGenre.builder().movie(newMovie).genre(gr).build();
            movieGenreRepository.save(movieGenre);
        }

        for (String feature : movie.getFeatures()) {
            Feature ft = featureRepository.findByFeatureName(feature).orElse(null);
            if (ft == null) {
                ft = Feature.builder().featureName(feature).build();
                ft = featureRepository.save(ft);

            }
            MovieFeature movieFeature = MovieFeature.builder().movie(newMovie).feature(ft).build();
            movieFeatureRepository.save(movieFeature);
        }

        movieRepository.save(newMovie);

    }

    @Override
    public List<Object> searchByScean(String input) {
        // TODO Auto-generated method stub
        // 미구현
        return null;
    }

    @Override
    public List<Object> searchByLine(String input) {
        // TODO Auto-generated method stub
        // 미구현
        return null;
    }

}
