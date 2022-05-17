package com.fhdufhdu.mim.controller;

import com.fhdufhdu.mim.dto.BoardDto;
import com.fhdufhdu.mim.dto.RequestBoardDto;
import com.fhdufhdu.mim.service.BoardService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards")
    public Page<BoardDto> getAllBoards(@RequestParam("page") int page) {
        return boardService.getAllBoards(page);
    }

    @GetMapping("/boards/title/{title}")
    public Page<BoardDto> getBoardsByTitle(@PathVariable String title, @RequestParam("page") int page) {
        return boardService.getBoardsByTitle(title, page);
    }

    @GetMapping("/boards/movie/{movieId}")
    public BoardDto getBoardByMovieId(@PathVariable Long movieId) {
        return boardService.getBoardByMovieId(movieId);
    }

    @GetMapping("/boards/{id}")
    public BoardDto getBoardById(@PathVariable Long id) {
        return boardService.getBoardById(id);
    }

    @DeleteMapping("/boards/{id}")
    public void shutDownBoard(@PathVariable Long id) {
        boardService.shutDownBoard(id);
    }

    @PostMapping("/request-boards/movie/{movieId}")
    public void countUpBoard(@PathVariable Long movieId) {
        boardService.countUpBoard(movieId);
    }

    @PostMapping("/request-boards/{id}")
    public void openUpBoard(@PathVariable Long id) {
        boardService.openUpBoard(id);
    }

    @DeleteMapping("/request-boards/{id}")
    public void cancelRequestBoard(@PathVariable Long id) {
        boardService.cancelRequestBoard(id);
    }

    @GetMapping("/request-boards")
    public Page<RequestBoardDto> getAllRequests(@RequestParam("page") int page) {
        return boardService.getAllRequests(page);
    }

    @GetMapping("/request-boards/{id}")
    public RequestBoardDto getRequestById(@PathVariable Long id) {
        return boardService.getRequestById(id);
    }
}
