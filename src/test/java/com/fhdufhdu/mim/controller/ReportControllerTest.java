package com.fhdufhdu.mim.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.dto.report.CommentReportSendDto;
import com.fhdufhdu.mim.dto.report.PostingReportSendDto;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.mock.WithMockCustomUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class ReportControllerTest {
    @Autowired
    WebApplicationContext wac;

    MockMvc mvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = Role.ADMIN)
    void 게시글신고_모두가져오기() throws Exception {
        ResultActions getAllPostingReports = mvc.perform(get("/report-postings").param("page", "0"));
        getAllPostingReports
                .andExpect(jsonPath("$.content.length()", is(1)));
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 게시글신고_모두가져오기_유저() throws Exception {
        ResultActions getAllPostingReports = mvc.perform(get("/report-postings").param("page", "0"));
        getAllPostingReports
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 게시글_신고하기() throws Exception {
        PostingReportSendDto report = PostingReportSendDto.builder()
                .postingId(1L)
                .reportReason("testReason")
                .build();

        ResultActions reportPosting = mvc.perform(post("/report-postings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(report)));
        reportPosting
                .andExpect(status().isOk());

    }

    @Test
    @WithMockCustomUser(username = "admin", roles = Role.ADMIN)
    void 게시글신고_처리완료() throws Exception {
        ResultActions confirmPostingReport = mvc.perform(put("/report-postings/1"));
        confirmPostingReport
                .andExpect(status().isOk());

        ResultActions getAllPostingReports = mvc.perform(get("/report-postings").param("page", "0"));
        getAllPostingReports
                .andExpect(jsonPath("$.content.length()", is(0)));
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 게시글신고_처리완료_유저() throws Exception {
        ResultActions confirmPostingReport = mvc.perform(put("/report-postings/1"));
        confirmPostingReport
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomUser(username = "admin", roles = Role.ADMIN)
    void 댓글신고_모두가져오기() throws Exception {
        ResultActions getAllPostingReports = mvc.perform(get("/report-comments").param("page", "0"));
        getAllPostingReports
                .andExpect(jsonPath("$.content.length()", is(1)));
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 댓글신고_모두가져오기_유저() throws Exception {
        ResultActions getAllPostingReports = mvc.perform(get("/report-comments").param("page", "0"));
        getAllPostingReports
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 댓글_신고하기() throws Exception {
        CommentReportSendDto report = CommentReportSendDto.builder()
                .commentId(1L)
                .reportReason("testReason")
                .build();

        ResultActions reportPosting = mvc.perform(post("/report-comments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(report)));
        reportPosting
                .andExpect(status().isOk());

    }

    @Test
    @WithMockCustomUser(username = "admin", roles = Role.ADMIN)
    void 댓글신고_처리완료() throws Exception {
        ResultActions confirmPostingReport = mvc.perform(put("/report-comments/1"));
        confirmPostingReport
                .andExpect(status().isOk());

        ResultActions getAllPostingReports = mvc.perform(get("/report-comments").param("page", "0"));
        getAllPostingReports
                .andExpect(jsonPath("$.content.length()", is(0)));
    }

    @Test
    @WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
    void 댓글신고_처리완료_유저() throws Exception {
        ResultActions confirmPostingReport = mvc.perform(put("/report-comments/1"));
        confirmPostingReport
                .andExpect(status().isForbidden());
    }

}
