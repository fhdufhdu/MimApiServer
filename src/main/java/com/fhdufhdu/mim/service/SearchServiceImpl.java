package com.fhdufhdu.mim.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.dto.MovieLineDto;
import com.fhdufhdu.mim.dto.SceanResultDto;
import com.fhdufhdu.mim.dto.ScriptDto;
import com.fhdufhdu.mim.dto.ScriptResultDto;
import com.fhdufhdu.mim.dto.SearchDto;
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

import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
    private static final ObjectMapper objectMapper = new ObjectMapper();

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
    public List<MovieDto> getMovieList(List<String> titles) {
        List<Movie> movies = movieRepository.findByTitleList(titles);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public List<MovieDto> getMovieListByIds(List<Long> ids) {
        List<Movie> movies = movieRepository.findByIdList(ids);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public Page<MovieDto> getMovieList(String title, int page, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "title");
        PageRequest pageRequest = PageRequest.of(page, size, sort);
        Page<Movie> movies = movieRepository.findByTitle(title, pageRequest);
        return convertToDests(movies, MovieDto.class);
    }

    @Override
    public InputStream getMovieBackground(Long movieId) throws FileNotFoundException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return new FileInputStream(movie.getDirName() + "/image.png");
    }

    @Override
    public InputStream getMoviePoster(Long movieId) throws FileNotFoundException {
        Movie movie = movieRepository.findById(movieId).orElseThrow(NotFoundMovieException::new);
        return new FileInputStream(movie.getDirName() + "/poster.png");
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
        MovieRating movieRating = movieRatingRepository.findByRating(movie.getRating()).orElseGet(() -> {
            MovieRating newMovieRating = MovieRating.builder()
                    .rating(movie.getRating())
                    .build();
            return movieRatingRepository.save(newMovieRating);
        });
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
            Director ds = directorRepository.findByName(director).orElseGet(() -> {
                Director newDs = Director.builder().name(director).build();
                return directorRepository.save(newDs);
            });
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
        MovieRating movieRating = movieRatingRepository.findByRating(movie.getRating()).orElseGet(() -> {
            MovieRating newMovieRating = MovieRating.builder()
                    .rating(movie.getRating())
                    .build();
            return movieRatingRepository.save(newMovieRating);
        });
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
    public List<MovieDto> searchByScean(String input) {
        List<String> titleList = getResultSceanSearch(input).getData();
        List<MovieDto> dbResult = getMovieList(titleList);
        List<MovieDto> result = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            for (int j = 0; j < dbResult.size(); j++) {
                if (titleList.get(i).equals(dbResult.get(j).getTitle())) {
                    result.add(dbResult.get(j));
                }
            }
        }
        return result;
    }

    @Override
    public List<MovieLineDto> searchByLine(String input) {
        List<ScriptDto> sResultList = getResultLineSearch(input).getData();
        List<String> titleList = new ArrayList<>();
        for (ScriptDto dto : sResultList) {
            titleList.add(dto.getTitle());
        }
        List<MovieDto> dbResult = getMovieList(titleList);
        List<MovieLineDto> result = new ArrayList<>();
        for (int i = 0; i < titleList.size(); i++) {
            for (int j = 0; j < dbResult.size(); j++) {
                if (titleList.get(i).equals(dbResult.get(j).getTitle())) {
                    result.add(MovieLineDto.builder()
                            .movieDto(dbResult.get(j))
                            .subtitles(sResultList.get(i).getSubtitle())
                            .build());
                }
            }
        }

        return result;
    }

    private SceanResultDto getResultSceanSearch(String query) {
        SearchDto data = SearchDto.builder()
                .type(true)
                .query(query)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = null;
        try {
            body = RequestBody.create(objectMapper.writeValueAsString(data),
                    MediaType.get("application/json; charset=utf-8"));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        Request request = new Request.Builder()
                .url("http://202.31.202.147:443/search")
                .post(body)
                .build();
        SceanResultDto responseData = null;
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();

                if (body != null) {
                    String jsonText = responseBody.string();

                    responseBody.close();

                    responseData = objectMapper.readValue(jsonText, SceanResultDto.class);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    private ScriptResultDto getResultLineSearch(String query) {
        SearchDto data = SearchDto.builder()
                .type(false)
                .query(query)
                .build();

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        RequestBody body = null;
        try {
            body = RequestBody.create(objectMapper.writeValueAsString(data),
                    MediaType.get("application/json; charset=utf-8"));
        } catch (JsonProcessingException e1) {
            e1.printStackTrace();
        }

        Request request = new Request.Builder()
                .url("http://202.31.202.147:443/search")
                .post(body)
                .build();
        ScriptResultDto responseData = null;
        try {
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();

                if (body != null) {
                    String jsonText = responseBody.string();

                    responseBody.close();

                    responseData = objectMapper.readValue(jsonText, ScriptResultDto.class);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }

}
