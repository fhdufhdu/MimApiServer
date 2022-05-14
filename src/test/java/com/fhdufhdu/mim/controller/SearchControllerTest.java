package com.fhdufhdu.mim.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
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
    void 영화리스트가져오기() throws Exception {
        ResultActions actions = mvc
                .perform(get("/movies/titles").param("titles", "movie1", "movie2").param("page", "0"));

        actions.andExpect(jsonPath("$.content.[0].title", is("movie1")))
                .andExpect(jsonPath("$.content.[1].title", is("movie2")));
    }

}
