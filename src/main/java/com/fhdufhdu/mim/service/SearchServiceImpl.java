package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.FeatureDto;
import com.fhdufhdu.mim.dto.GenreDto;
import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieRatingDto;
import com.fhdufhdu.mim.dto.worker.WorkerDto;
import com.fhdufhdu.mim.entity.Feature;
import com.fhdufhdu.mim.entity.Genre;
import com.fhdufhdu.mim.entity.Movie;
import com.fhdufhdu.mim.entity.MovieFeature;
import com.fhdufhdu.mim.entity.MovieGenre;
import com.fhdufhdu.mim.entity.MovieRating;
import com.fhdufhdu.mim.entity.MovieWorker;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.entity.worker.Actor;
import com.fhdufhdu.mim.entity.worker.Director;
import com.fhdufhdu.mim.entity.worker.Writer;
import com.fhdufhdu.mim.exception.NotFoundMovieException;
import com.fhdufhdu.mim.exception.WrongPermissionException;
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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchServiceImpl extends UtilService implements SearchService {
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
    public MovieDto getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return convertToDest(movie, MovieDto.class);
    }

    @Override
    public List<WorkerDto> getDirectorByMovieId(Long movieId) {
        List<Director> director = directorRepository.findByMovieId(movieId);
        return convertToDests(director, WorkerDto.class);
    }

    @Override
    public List<WorkerDto> getActorByMovieId(Long movieId) {
        List<Actor> actors = actorRepository.findByMovieId(movieId);
        return convertToDests(actors, WorkerDto.class);
    }

    @Override
    public List<WorkerDto> getWriterByMovieId(Long movieId) {
        List<Writer> writers = writerRepository.findByMovieId(movieId);
        return convertToDests(writers, WorkerDto.class);
    }

    @Override
    public List<GenreDto> getGenreByMovieId(Long movieId) {
        List<Genre> genres = genreRepository.findByMovieId(movieId);
        return convertToDests(genres, GenreDto.class);
    }

    @Override
    public List<FeatureDto> getFeatureByMovieId(Long movieId) {
        List<Feature> features = featureRepository.findByMovieId(movieId);
        return convertToDests(features, FeatureDto.class);
    }

    @Override
    public MovieRatingDto getMovieRatingByMovieId(Long movieId) {
        MovieRating rating = movieRatingRepository.findByMovieId(movieId).get();
        return convertToDest(rating, MovieRatingDto.class);
    }

    @Override
    public List<MovieDto> getMovieList(List<String> titles) {
        List<Movie> movies = movieRepository.findByTitieList(titles);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public void removeMovie(Long movieId) {
        if (!hasPermission(Role.ADMIN)) {
            throw new WrongPermissionException();
        }
        movieRepository.deleteById(movieId);
    }

    @Override
    public void changeMovieInfo(Long movieId, MovieDto movie, List<String> directors, List<String> actors,
            List<String> writers,
            List<String> genres, List<String> features, String rating) {

        if (!hasPermission(Role.ADMIN)) {
            throw new WrongPermissionException();
        }
        Movie originalMovie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        MovieRating movieRating = movieRatingRepository.findByRating(rating).get();
        originalMovie.setTitle(movie.getTitle());
        originalMovie.setEngTitle(movie.getEngTitle());
        originalMovie.setYear(movie.getYear());
        originalMovie.setSynopsis(movie.getSynopsis());
        originalMovie.setRunningTime(movie.getRunningTime());
        originalMovie.setDirName(movie.getDirName());
        originalMovie.setMovieRating(movieRating);

        for (String director : directors) {
            Director ds = directorRepository.findByName(director).get();
            if (ds == null) {
                ds = Director.builder().name(director).build();
                ds = directorRepository.save(ds);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(ds).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String actor : actors) {
            Actor ac = actorRepository.findByName(actor).get();
            if (ac == null) {
                ac = Actor.builder().name(actor).build();
                ac = actorRepository.save(ac);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(ac).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String writer : writers) {
            Writer wt = writerRepository.findByName(writer).get();
            if (wt == null) {
                wt = Writer.builder().name(writer).build();
                wt = writerRepository.save(wt);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(originalMovie).worker(wt).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String genre : genres) {
            Genre gr = genreRepository.findByGenreName(genre).get();
            if (gr == null) {
                gr = Genre.builder().genreName(genre).build();
                gr = genreRepository.save(gr);
            }
            MovieGenre movieGenre = MovieGenre.builder().movie(originalMovie).genre(gr).build();
            movieGenreRepository.save(movieGenre);
        }

        for (String feature : features) {
            Feature ft = featureRepository.findByFeatureName(feature).get();
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
    public void addMovie(MovieDto movie, List<String> directors, List<String> actors, List<String> writers,
            List<String> genres, List<String> features, String rating) {
        MovieRating movieRating = movieRatingRepository.findByRating(rating).get();
        Movie newMovie = Movie.builder()
                .title(movie.getTitle())
                .engTitle(movie.getEngTitle())
                .year(movie.getYear())
                .synopsis(movie.getSynopsis())
                .runningTime(movie.getRunningTime())
                .movieRating(movieRating)
                .dirName(movie.getDirName())
                .build();

        for (String director : directors) {
            Director ds = directorRepository.findByName(director).get();
            if (ds == null) {
                ds = Director.builder().name(director).build();
                ds = directorRepository.save(ds);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(ds).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String actor : actors) {
            Actor ac = actorRepository.findByName(actor).get();
            if (ac == null) {
                ac = Actor.builder().name(actor).build();
                ac = actorRepository.save(ac);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(ac).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String writer : writers) {
            Writer wt = writerRepository.findByName(writer).get();
            if (wt == null) {
                wt = Writer.builder().name(writer).build();
                wt = writerRepository.save(wt);
            }
            MovieWorker movieWorker = MovieWorker.builder().movie(newMovie).worker(wt).build();
            movieWorkerRepository.save(movieWorker);
        }

        for (String genre : genres) {
            Genre gr = genreRepository.findByGenreName(genre).get();
            if (gr == null) {
                gr = Genre.builder().genreName(genre).build();
                gr = genreRepository.save(gr);
            }
            MovieGenre movieGenre = MovieGenre.builder().movie(newMovie).genre(gr).build();
            movieGenreRepository.save(movieGenre);
        }

        for (String feature : features) {
            Feature ft = featureRepository.findByFeatureName(feature).get();
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
    public List<MovieDto> searchByScean(String input) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<MovieDto> searchByLine(String input) {
        // TODO Auto-generated method stub
        return null;
    }

}
