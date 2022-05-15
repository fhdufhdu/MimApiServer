package com.fhdufhdu.mim.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.mock.WithMockCustomUser;
import com.jayway.jsonpath.JsonPath;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@Slf4j
public class SearchControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void 영화상세정보() throws Exception {
        ResultActions actions = mvc.perform(get("/movies/1"));

        actions.andExpect(jsonPath("$.movieDto.title", is("movie1")))
                .andExpect(jsonPath("$.directors.[0]", is("director1")));
    }

    @Test
    void 타이틀리스트로_영화가져오기() throws Exception {
        ResultActions actions = mvc
                .perform(get("/movies/titles").param("titles", "movie1", "movie2").param("page", "0"));

        actions.andExpect(jsonPath("$.content.[0].title", is("movie1")))
                .andExpect(jsonPath("$.content.[1].title", is("movie2")));
    }

    @Test
    void 타이틀로_영화검색() throws Exception {
        ResultActions actions = mvc
                .perform(get("/movies").param("title", "movie").param("page", "0"));

        actions.andExpect(jsonPath("$.content.length()", is(3)));
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 영화수정() throws Exception {
        String[] directors = { "director2" };
        String[] actors = { "actor2" };
        String[] writers = { "writer200" };

        String[] features = { "fname1" };
        String[] genres = { "gname1" };

        String rating = "15";

        MovieDto movieDto = MovieDto.builder()
                .title("movie4")
                .build();
        MovieDtoV2 movieDtoV2 = MovieDtoV2.builder()
                .directors(new ArrayList<String>(Arrays.asList(directors)))
                .actors(new ArrayList<String>(Arrays.asList(actors)))
                .writers(new ArrayList<String>(Arrays.asList(writers)))
                .features(new ArrayList<String>(Arrays.asList(features)))
                .genres(new ArrayList<String>(Arrays.asList(genres)))
                .rating(rating)
                .movieDto(movieDto)
                .build();
        ResultActions actions = mvc
                .perform(put("/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDtoV2)));

        actions.andExpect(status().isOk());

        ResultActions actions1 = mvc.perform(get("/movies/1"));

        actions1.andExpect(jsonPath("$.movieDto.title", is("movie4")))
                .andExpect(jsonPath("$.directors.length()", is(1)))
                .andExpect(jsonPath("$.directors.[0]", is("director2")))
                .andExpect(jsonPath("$.actors.length()", is(1)))
                .andExpect(jsonPath("$.actors.[0]", is("actor2")))
                .andExpect(jsonPath("$.writers.length()", is(1)))
                .andExpect(jsonPath("$.writers.[0]", is("writer200")))
                .andExpect(jsonPath("$.features.length()", is(1)))
                .andExpect(jsonPath("$.features.[0]", is("fname1")))
                .andExpect(jsonPath("$.genres.length()", is(1)))
                .andExpect(jsonPath("$.genres.[0]", is("gname1")))
                .andExpect(jsonPath("$.rating", is("15")));
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 영화추가() throws Exception {
        String[] directors = { "director2", "director1" };
        String[] actors = { "actor2", "actor30" };
        String[] writers = { "writer200" };

        String[] features = { "fname1", "fname100" };
        String[] genres = { "gname1", "gname200" };

        String rating = "15";

        MovieDto movieDto = MovieDto.builder()
                .title("movie5")
                .build();
        MovieDtoV2 movieDtoV2 = MovieDtoV2.builder()
                .directors(new ArrayList<String>(Arrays.asList(directors)))
                .actors(new ArrayList<String>(Arrays.asList(actors)))
                .writers(new ArrayList<String>(Arrays.asList(writers)))
                .features(new ArrayList<String>(Arrays.asList(features)))
                .genres(new ArrayList<String>(Arrays.asList(genres)))
                .rating(rating)
                .movieDto(movieDto)
                .build();
        ResultActions actions = mvc
                .perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieDtoV2)));
        actions.andExpect(status().isOk());

        MvcResult result = mvc
                .perform(get("/movies").param("title", "movie5").param("page", "0")).andReturn();
        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.content.[0].id").toString();

        ResultActions actions1 = mvc.perform(get("/movies/" + id));

        actions1.andExpect(jsonPath("$.movieDto.title", is("movie5")))
                .andExpect(jsonPath("$.directors.length()", is(2)))
                .andExpect(jsonPath("$.directors",
                        containsInAnyOrder("director1", "director2")))
                .andExpect(jsonPath("$.actors.length()", is(2)))
                .andExpect(jsonPath("$.actors",
                        containsInAnyOrder("actor2", "actor30")))
                .andExpect(jsonPath("$.writers.length()", is(1)))
                .andExpect(jsonPath("$.writers",
                        containsInAnyOrder("writer200")))
                .andExpect(jsonPath("$.features.length()", is(2)))
                .andExpect(jsonPath("$.features",
                        containsInAnyOrder("fname1",
                                "fname100")))
                .andExpect(jsonPath("$.genres.length()", is(2)))
                .andExpect(jsonPath("$.genres",
                        containsInAnyOrder("gname1",
                                "gname200")))
                .andExpect(jsonPath("$.rating", is("15")));
    }

}
