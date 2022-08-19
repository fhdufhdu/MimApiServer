package com.fhdufhdu.mim.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.dto.comment.CommentAddDto;
import com.fhdufhdu.mim.dto.comment.CommentModifyDto;
import com.fhdufhdu.mim.dto.post.PostingAddDto;
import com.fhdufhdu.mim.dto.post.PostingModifyDto;
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
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PostControllerTest {
	@Autowired
	WebApplicationContext wac;

	MockMvc mvc;
	ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
	}

	@Test
	void 해당게시판_게시글불러오기() throws Exception {
		ResultActions getAllPostings = mvc.perform(get("/postings/board/1").param("page", "0"));
		getAllPostings.andExpect(jsonPath("$.content.length()", is(4)));
	}

	@Test
	void 게시판아이디와_게시글번호로_게시글가져오기() throws Exception {
		ResultActions getPostingByBoardAndPostingNum = mvc.perform(get("/postings/board/1/posting-number/3"));
		getPostingByBoardAndPostingNum.andExpect(jsonPath("$.id", is(3)));

		ResultActions getPostingByBoardAndPostingNum1 = mvc.perform(get("/postings/board/1/posting-number/4"));
		getPostingByBoardAndPostingNum1.andExpect(status().isBadRequest());
	}

	@Test
	void 게시글아이디로_가져오기() throws Exception {
		ResultActions getPostingById = mvc.perform(get("/postings/3"));
		getPostingById.andExpect(jsonPath("$.id", is(3)));

		ResultActions getPostingById1 = mvc.perform(get("/postings/4"));
		getPostingById1.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 게시글_수정() throws Exception {
		PostingModifyDto modifiedPosting = PostingModifyDto.builder()
				.title("testTitle")
				.content("testContent")
				.build();

		ResultActions modifyPosting = mvc.perform(put("/postings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedPosting)));
		modifyPosting.andExpect(status().isOk());

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is("testTitle")))
				.andExpect(jsonPath("$.content", is("testContent")));

	}

	@Test
	@WithMockCustomUser(username = "admin", roles = Role.ADMIN)
	void 게시글_수정_관리자() throws Exception {
		PostingModifyDto modifiedPosting = PostingModifyDto.builder()
				.title("testTitle")
				.content("testContent")
				.build();

		ResultActions modifyPosting = mvc.perform(put("/postings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedPosting)));
		modifyPosting.andExpect(status().isOk());

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.title", is("testTitle")))
				.andExpect(jsonPath("$.content", is("testContent")));

	}

	@Test
	@WithMockCustomUser(username = "another", roles = Role.USER)
	void 게시글_수정_타인() throws Exception {
		PostingModifyDto modifiedPosting = PostingModifyDto.builder()
				.title("testTitle")
				.content("testContent")
				.build();

		ResultActions modifyPosting = mvc.perform(put("/postings/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedPosting)));
		modifyPosting.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 게시글_삭제() throws Exception {
		ResultActions removePosting = mvc.perform(delete("/postings/1"));
		removePosting.andExpect(status().isOk());

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "admin", roles = Role.ADMIN)
	void 게시글_삭제_관리자() throws Exception {
		ResultActions removePosting = mvc.perform(delete("/postings/1"));
		removePosting.andExpect(status().isOk());

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "another", roles = Role.USER)
	void 게시글_삭제_타인() throws Exception {
		ResultActions removePosting = mvc.perform(delete("/postings/1"));
		removePosting.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 게시글_작성() throws Exception {
		PostingAddDto addedPosting = PostingAddDto.builder()
				.movieBoardId(1L)
				.title("testTitle")
				.content("testContent")
				.userId("fhdufhdu")
				.build();

		ResultActions modifyPosting = mvc.perform(post("/postings")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addedPosting)));
		modifyPosting.andExpect(status().isOk());

		ResultActions getPostingById = mvc.perform(get("/postings/board/1/posting-number/6"));
		getPostingById
				.andExpect(jsonPath("$.title", is("testTitle")))
				.andExpect(jsonPath("$.content", is("testContent")));

		ResultActions getBoardById = mvc.perform(get("/boards/1"));
		getBoardById.andExpect(jsonPath("$.lastPostingNumber", is(6)));
	}

	@Test
	void 해당게시글_댓글불러오기() throws Exception {
		ResultActions getAllCommentsByPostingId = mvc.perform(get("/comments/posting/1").param("page", "0"));
		getAllCommentsByPostingId
				.andExpect(jsonPath("$.content.length()", is(5)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[2].id", is(2)))
				.andExpect(jsonPath("$.content.[3].id", is(3)));
	}

	@Test
	void 게시판아이디와_게시글번호로_댓글가져오기() throws Exception {
		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(5)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[2].id", is(2)))
				.andExpect(jsonPath("$.content.[3].id", is(3)));

		ResultActions getPostingByBoardAndPostingNum1 = mvc.perform(get("/comments/board/1/posting-number/1"));
		getPostingByBoardAndPostingNum1.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 댓글_수정() throws Exception {
		CommentModifyDto modifiedComment = CommentModifyDto.builder()
				.content("testContent")
				.build();

		ResultActions modifyComment = mvc.perform(put("/comments/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedComment)));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(5)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[2].id", is(2)))
				.andExpect(jsonPath("$.content.[3].id", is(3)));

	}

	@Test
	@WithMockCustomUser(username = "admin", roles = Role.ADMIN)
	void 댓글_수정_관리자() throws Exception {
		CommentModifyDto modifiedComment = CommentModifyDto.builder()
				.content("testContent")
				.build();

		ResultActions modifyComment = mvc.perform(put("/comments/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedComment)));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(5)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[0].content", is("testContent")))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[2].id", is(2)))
				.andExpect(jsonPath("$.content.[3].id", is(3)));

	}

	@Test
	@WithMockCustomUser(username = "another", roles = Role.USER)
	void 댓글_수정_타인() throws Exception {
		CommentModifyDto modifiedComment = CommentModifyDto.builder()
				.content("testContent")
				.build();

		ResultActions modifyComment = mvc.perform(put("/comments/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(modifiedComment)));
		modifyComment.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 댓글_삭제() throws Exception {
		ResultActions modifyComment = mvc.perform(delete("/comments/1"));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(4)))
				.andExpect(jsonPath("$.content.[0].id", is(4)))
				.andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[2].id", is(3)));

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.commentCnt", is(4)));
	}

	@Test
	@WithMockCustomUser(username = "admin", roles = Role.ADMIN)
	void 댓글_삭제_관리자() throws Exception {
		ResultActions modifyComment = mvc.perform(delete("/comments/1"));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(4)))
				.andExpect(jsonPath("$.content.[0].id", is(4)))
				.andExpect(jsonPath("$.content.[1].id", is(2)))
				.andExpect(jsonPath("$.content.[2].id", is(3)));

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.commentCnt", is(4)));

	}

	@Test
	@WithMockCustomUser(username = "another", roles = Role.USER)
	void 댓글_삭제_타인() throws Exception {
		ResultActions modifyComment = mvc.perform(delete("/comments/1"));
		modifyComment.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 댓글_등록() throws Exception {
		CommentAddDto addedComment = CommentAddDto.builder()
				.postingId(1L)
				.depth(0)
				.content("testContent")
				.build();

		ResultActions modifyComment = mvc.perform(post("/comments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addedComment)));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(6)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[2].id", is(2)))
				.andExpect(jsonPath("$.content.[3].id", is(3)));

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.commentCnt", is(6)));

	}

	@Test
	@WithMockCustomUser(username = "fhdufhdu", roles = Role.USER)
	void 대댓글_등록() throws Exception {
		CommentAddDto addedComment = CommentAddDto.builder()
				.postingId(1L)
				.depth(1)
				.commentGroup(1L)
				.content("testContent")
				.build();

		ResultActions modifyComment = mvc.perform(post("/comments")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(addedComment)));
		modifyComment.andExpect(status().isOk());

		ResultActions getAllCommentsByBoardAndPostingNum = mvc.perform(get("/comments/board/1/posting-number/1")
				.param("page", "0"));
		getAllCommentsByBoardAndPostingNum.andExpect(jsonPath("$.content.length()", is(6)))
				.andExpect(jsonPath("$.content.[0].id", is(1)))
				.andExpect(jsonPath("$.content.[1].id", is(4)))
				.andExpect(jsonPath("$.content.[3].id", is(2)))
				.andExpect(jsonPath("$.content.[4].id", is(3)));

		ResultActions getPostingById = mvc.perform(get("/postings/1"));
		getPostingById.andExpect(jsonPath("$.commentCnt", is(6)));

	}

	@Test
	void 특정유저가_작성한_게시글_조회() throws Exception {
		ResultActions getPostingsByUserId = mvc.perform(get("/postings/user/fhdufhdu").param("page", "0"));
		getPostingsByUserId
				.andExpect(jsonPath("$.content.length()", is(3)));
	}

	@Test
	void 특정유저가_작성한_댓글_조회() throws Exception {
		ResultActions getCommentsByUserId = mvc.perform(get("/comments/user/fhdufhdu").param("page", "0"));
		getCommentsByUserId
				.andExpect(jsonPath("$.content.length()", is(4)));
	}
}
