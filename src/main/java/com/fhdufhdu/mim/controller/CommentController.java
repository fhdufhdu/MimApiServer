package com.fhdufhdu.mim.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.comment.CommentChange;
import com.fhdufhdu.mim.dto.comment.CommentInfo;
import com.fhdufhdu.mim.dto.comment.CommentWriting;
import com.fhdufhdu.mim.service.CommentService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments/posts/{postId}")
    public Page<CommentInfo> getCommentsByPostId(@PathVariable("postId") Long postId,
            @ModelAttribute PageParam pageParam) {
        return commentService.getCommentsByPostId(postId, pageParam);
    }

    @GetMapping("/comments/members/{memberId}")
    public Page<CommentInfo> getcommnetsByMemberId(@PathVariable("memberId") String memberId,
            @ModelAttribute PageParam pageParam) {
        return commentService.getCommentsByMemberId(memberId, pageParam);
    }

    @PutMapping("/comments/{commentId}")
    public CommentInfo changeComment(@PathVariable("commentId") Long commentId, @RequestBody CommentChange comment) {
        return commentService.changeComment(commentId, comment);
    }

    @DeleteMapping("/comments/{commentId}")
    public boolean removeComment(@PathVariable("commentId") Long commentId) {
        return commentService.removeComment(commentId);
    }

    @PostMapping("/comments")
    public CommentInfo writeComment(@RequestBody CommentWriting comment) {
        return commentService.writeComment(comment);
    }

}
