package com.fhdufhdu.mim.controller;

import com.fhdufhdu.mim.dto.RequestBoardDto;
import com.fhdufhdu.mim.dto.board.BoardDto;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Api(tags = { "게시판 관리", "게시판 요청 관리" })
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards")
    @ApiOperation(value = "[다건 조회] 모든 게시판 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "게시판 관리")
    public Page<BoardDto> getAllBoards(@RequestParam("page") int page, @RequestParam("size") int size) {
        return boardService.getAllBoards(page, size);
    }

    @GetMapping("/boards/title/{title}")
    @ApiOperation(value = "[다건 조회] 영화 제목으로 게시판 조회(일부 문자열로 가능)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "영화 제목", paramType = "path", required = true)
    })
    @Tag(name = "게시판 관리")
    public Page<BoardDto> getBoardsByTitle(@PathVariable String title, @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return boardService.getBoardsByTitle(title, page, size);
    }

    @GetMapping("/boards/movie/{movieId}")
    @ApiOperation(value = "[단건 조회] 영화 아이디로 게시판 조회")
    @ApiImplicitParam(name = "movieId", value = "영화 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 관리")
    public BoardDto getBoardByMovieId(@PathVariable Long movieId) {
        return boardService.getBoardByMovieId(movieId);
    }

    @GetMapping("/boards/{id}")
    @ApiOperation(value = "[단건 조회] 게시판 아이디로 게시판 조회")
    @ApiImplicitParam(name = "id", value = "게시판 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 관리")
    public BoardDto getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @DeleteMapping("/boards/{id}")
    @ApiOperation(value = "[삭제] 게시판 폐쇄")
    @ApiImplicitParam(name = "id", value = "게시판 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 관리")
    public void shutDownBoard(@PathVariable Long id) {
        boardService.shutDownBoard(id);
    }

    @PostMapping("/request-boards/movie/{movieId}")
    @ApiOperation(value = "[등록] 게시판 요청")
    @ApiImplicitParam(name = "movieId", value = "영화 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 요청 관리")
    public void countUpBoard(@PathVariable Long movieId) {
        boardService.countUpBoard(movieId);
    }

    @PostMapping("/request-boards/{id}")
    @ApiOperation(value = "[등록] 요청 게시판을 일반 게시판으로 승격")
    @ApiImplicitParam(name = "id", value = "게시판 요청 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 요청 관리")
    public void openUpBoard(@PathVariable Long id) {
        boardService.openUpBoard(id);
    }

    @DeleteMapping("/request-boards/{id}")
    @ApiOperation(value = "[삭제] 게시판 요청 반려")
    @ApiImplicitParam(name = "id", value = "게시판 요청 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 요청 관리")
    public void cancelRequestBoard(@PathVariable Long id) {
        boardService.cancelRequestBoard(id);
    }

    @GetMapping("/request-boards")
    @ApiOperation(value = "[다건 조회] 게시판 요청 모두 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })

    @Tag(name = "게시판 요청 관리")
    public Page<RequestBoardDto> getAllRequests(@RequestParam("page") int page, @RequestParam("size") int size) {
        return boardService.getAllRequests(page, size);
    }

    @GetMapping("/request-boards/{id}")
    @ApiOperation(value = "[단건 조회] 게시판 요청 조회")
    @ApiImplicitParam(name = "id", value = "게시판 요청 아이디", paramType = "path", required = true)
    @Tag(name = "게시판 요청 관리")
    public RequestBoardDto getRequestById(@PathVariable Long id) {
        return boardService.getRequestById(id);
    }
}
