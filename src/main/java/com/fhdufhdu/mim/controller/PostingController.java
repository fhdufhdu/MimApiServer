package com.fhdufhdu.mim.controller;

import com.fhdufhdu.mim.dto.CommentDto;
import com.fhdufhdu.mim.dto.PostingDto;
import com.fhdufhdu.mim.service.PostingService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostingController {
    private final PostingService postingService;

    @GetMapping("/postings/board/{boardId}")
    public Page<PostingDto> getAllPostings(@PathVariable Long boardId, @RequestParam("page") int page) {
        return postingService.getAllPostings(boardId, page);
    }

    @GetMapping("/postings/board/{boardId}/posting-number/{postingNumber}")
    public PostingDto getPostingByBoardAndPostingNum(@PathVariable Long boardId, @PathVariable Long postingNumber) {
        return postingService.getPostingByBoardAndPostingNum(boardId, postingNumber);
    }

    @GetMapping("/postings/{id}")
    public PostingDto getPostingById(@PathVariable Long id) {
        return postingService.getPostingById(id);
    }

    @PutMapping("/postings/{id}")
    public void modifyPosting(@PathVariable Long id, @RequestBody PostingDto postingDto) {
        postingService.modifyPosting(id, postingDto);
    }

    @DeleteMapping("/postings/{id}")
    public void removiePosting(@PathVariable Long id) {
        postingService.removePosting(id);
    }

    @PostMapping("/postings")
    public void addPosting(@RequestBody PostingDto postingDto) {
        postingService.addPosting(postingDto);
    }

    @GetMapping("/comments/board/{boardId}/posting-number/{postingNumber}")
    public Page<CommentDto> getAllCommentsByBoardAndPostingNum(@PathVariable Long boardId,
            @PathVariable Long postingNumber, @RequestParam("page") int page) {
        return postingService.getAllCommentsByBoardAndPostingNum(boardId, postingNumber, page);
    }

    @GetMapping("/comments/posting/{postingId}")
    public Page<CommentDto> getAllCommentsByPostingId(@PathVariable Long postingId, @RequestParam("page") int page) {
        return postingService.getAllCommentsByPostingId(postingId, page);
    }

    @PutMapping("/comments/{id}")
    public void modifyComment(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        postingService.modifyComment(id, commentDto);
    }

    @DeleteMapping("/comments/{id}")
    public void removeComment(@PathVariable Long id) {
        postingService.removeComment(id);
    }

    @PostMapping("/comments")
    public void addComment(@RequestBody CommentDto commentDto) {
        postingService.addComment(commentDto);
    }

}
