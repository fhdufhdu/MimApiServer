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

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class BoardControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity()).build();
    }

    @Test
    void 게시판_리스트_가져오기() throws Exception {
        ResultActions getAllBoards = mvc.perform(get("/boards").param("page", "0"));

        getAllBoards.andExpect(jsonPath("$.content.length()", is(2)));
    }

    @Test
    void 영화제목_게시판_리스트() throws Exception {
        ResultActions getBoardsByTitle = mvc.perform(get("/boards/title/movie3").param("page", "0"));

        getBoardsByTitle.andExpect(jsonPath("$.content.", matcher))
    }

}
