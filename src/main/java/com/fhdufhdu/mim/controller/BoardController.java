package com.fhdufhdu.mim.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    // private final BoardService boardService;

    @GetMapping("/boards")
    public String getAllBoards() {
        return "1";
    }

    @GetMapping(value = "/boards", params = { "title" })
    public String getBoardsByTitle(@RequestParam("title") String title) {
        return "2";
    }

}
