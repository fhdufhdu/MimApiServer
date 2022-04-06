package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Movie;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import lombok.extern.slf4j.Slf4j;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Slf4j
public class MovieRepoTest {
    @Autowired
    MovieRepository repository;

    @Test
    public void 영화데이터삽입_테스트() {
        Movie movie = new Movie(1L, "테스트 타이틀", "test title", 2022, "테스트 시놉시스", "1시간 1분", null, "testDir");
        Movie savedMovie = repository.save(movie);
        log.info("title:{}", savedMovie.getTitle());
    }

}
