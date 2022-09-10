package com.fhdufhdu.mim.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.post.PostChange;
import com.fhdufhdu.mim.dto.post.PostInfo;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.dto.post.PostWriting;
import com.fhdufhdu.mim.service.PostService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public Page<PostListElem> getPostsByQuery(@ModelAttribute PageParam pageParam, @RequestParam("query") String query,
            @RequestParam("type") String type) {
        return postService.getPostsByQuery(query, type, pageParam);
    }

    @GetMapping("/posts/{postId}")
    public PostInfo getPostById(@PathVariable("postId") Long id) {
        return postService.getPostById(id);
    }

    @PutMapping("/posts/{postId}")
    public PostInfo modifyPost(@PathVariable("postId") Long id, @RequestBody PostChange post) {
        return postService.modifyPost(id, post);
    }

    @DeleteMapping("/posts/{postId}")
    public boolean removePost(@PathVariable("postId") Long id) {
        return postService.removePost(id);
    }

    @PostMapping("/posts")
    public PostInfo writePost(@RequestBody PostWriting post) {
        return postService.writePost(post);
    }
}
