package com.fhdufhdu.mim.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.mock.WithMockCustomUser;

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
        getAllBoards.andExpect(jsonPath("$.content.length()", is(1)));
    }

    @Test
    void 영화제목_게시판_리스트() throws Exception {
        ResultActions getBoardsByTitle = mvc.perform(get("/boards/title/movie3").param("page", "0"));
        getBoardsByTitle.andExpect(jsonPath("$.content.[0].movieId", is(3)));
    }

    @Test
    void 영화아이디_게시판() throws Exception {
        ResultActions getBoardByMovieId = mvc.perform(get("/boards/movie/3"));
        getBoardByMovieId.andExpect(jsonPath("$.movieId", is(3)));
    }

    @Test
    void 아이디로_게시판() throws Exception {
        ResultActions getBoardById = mvc.perform(get("/boards/1"));
        getBoardById.andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 게시판_폐쇄() throws Exception {
        ResultActions shutDownBoard = mvc.perform(delete("/boards/1"));
        shutDownBoard.andExpect(status().isOk());

        ResultActions getBoardById = mvc.perform(get("/boards/1"));
        getBoardById.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 영화개설신청하기() throws Exception {
        ResultActions countUpBoard = mvc.perform(post("/request-boards/movie/1"));
        countUpBoard.andExpect(status().isOk());
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 영화게시판_개설하기() throws Exception {
        ResultActions openUpBoard = mvc.perform(post("/request-boards/2"));
        openUpBoard.andExpect(status().isOk());

        ResultActions openUpBoard1 = mvc.perform(post("/request-boards/1"));
        openUpBoard1.andExpect(status().isBadRequest());

        ResultActions openUpBoard2 = mvc.perform(post("/request-boards/3"));
        openUpBoard2.andExpect(status().isBadRequest());
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 게시판_요청반려() throws Exception {
        ResultActions cancelRequestBoard = mvc.perform(post("/request-boards/2"));
        cancelRequestBoard.andExpect(status().isOk());

        ResultActions cancelRequestBoard1 = mvc.perform(post("/request-boards/2"));
        cancelRequestBoard1.andExpect(status().isBadRequest());

    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 모든_게시판요청_보기() throws Exception {
        ResultActions getAllRequests = mvc.perform(get("/request-boards").param("page", "0"));
        getAllRequests.andExpect(jsonPath("$.content.length()", is(1)))
                .andExpect(jsonPath("$.content.[0].id", is(2)));
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = { Role.ADMIN })
    void 게시판요청_아이디로보기() throws Exception {
        ResultActions getRequestById = mvc.perform(get("/request-boards/2"));
        getRequestById.andExpect(jsonPath("$.id", is(2)));
    }
}
