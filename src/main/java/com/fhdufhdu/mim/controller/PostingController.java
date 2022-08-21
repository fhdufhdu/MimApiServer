package com.fhdufhdu.mim.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.comment.WrittenComment;
import com.fhdufhdu.mim.dto.comment.CommentDto;
import com.fhdufhdu.mim.dto.comment.ModifiedComment;
import com.fhdufhdu.mim.dto.post.WrittenPost;
import com.fhdufhdu.mim.dto.post.ModifiedPost;
import com.fhdufhdu.mim.dto.post.PostListElem;
import com.fhdufhdu.mim.service.PostingService;

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
@Api(tags = { "게시글 관리", "댓글 관리", "유저별 게시글, 댓글 확인" })
public class PostingController {
    private final PostingService postingService;

    @GetMapping("/postings/board/{boardId}")
    @ApiOperation(value = "[다건 조회] 해당 게시판의 모든 게시글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "boardId", value = "게시판 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "게시글 관리")
    public Page<PostingDto> getAllPostings(@PathVariable Long boardId, @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return postingService.getAllPostings(boardId, page, size);
    }

    @GetMapping("/postings/board/{boardId}/posting-number/{postingNumber}")
    @ApiOperation(value = "[단건 조회] 해당 게시판과 게시글 번호로 게시글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardId", value = "게시판 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "postingNumber", value = "게시글 번호", paramType = "path", required = true)
    })
    @Tag(name = "게시글 관리")
    public PostingDto getPostingByBoardAndPostingNum(@PathVariable Long boardId, @PathVariable Long postingNumber) {
        return postingService.getPostingByBoardAndPostingNum(boardId, postingNumber);
    }

    @GetMapping("/postings/board/{boardId}/query")
    @ApiOperation(value = "[다건 조회] 해당 게시판에서 제목으로 조회(문자열 일부만 가능)")
    @ApiImplicitParams({

            @ApiImplicitParam(name = "boardId", value = "게시판 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "query", value = "제목 일부", paramType = "query", required = true),
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "게시글 관리")
    public Page<PostingDto> getPostingByQuery(@PathVariable Long boardId, @RequestParam("query") String query,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return postingService.getPostingByQuery(boardId, query, page, size);
    }

    @GetMapping("/postings/{id}")
    @ApiOperation(value = "[단건 조회] 게시글 아이디로 게시글 조회")
    @ApiImplicitParam(name = "id", value = "게시글 아이디", paramType = "path", required = true)
    @Tag(name = "게시글 관리")
    public PostingDto getPostingById(@PathVariable Long id) {
        return postingService.getPostingById(id);
    }

    @PutMapping("/postings/{id}")
    @ApiOperation(value = "[수정] 게시글 수정")
    @ApiImplicitParam(name = "id", value = "게시글 아이디", paramType = "path", required = true)
    @Tag(name = "게시글 관리")
    public void modifyPosting(@PathVariable Long id,
            @RequestBody ModifiedPost postingDto) {
        postingService.modifyPosting(id, postingDto);
    }

    @DeleteMapping("/postings/{id}")
    @ApiOperation(value = "[삭제] 게시글 삭제")
    @ApiImplicitParam(name = "id", value = "게시글 아이디", paramType = "path", required = true)
    @Tag(name = "게시글 관리")
    public void removePosting(@PathVariable Long id) {
        postingService.removePosting(id);
    }

    @PostMapping("/postings")
    @ApiOperation(value = "[등록] 게시글 작성")
    @Tag(name = "게시글 관리")
    public void addPosting(@RequestBody WrittenPost postingDto) {
        postingService.addPosting(postingDto);
    }

    @GetMapping("/comments/board/{boardId}/posting-number/{postingNumber}")
    @ApiOperation(value = "[다건 조회] 게시판 아이디와 게시글 번호로 모든 댓글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "boardId", value = "게시판 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "postingNumber", value = "게시글 번호", paramType = "path", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "댓글 관리")
    public Page<CommentDto> getAllCommentsByBoardAndPostingNum(@PathVariable Long boardId,
            @PathVariable Long postingNumber, @RequestParam("page") int page, @RequestParam("size") int size) {
        return postingService.getAllCommentsByBoardAndPostingNum(boardId, postingNumber, page, size);
    }

    @GetMapping("/comments/posting/{postingId}")
    @ApiOperation(value = "[다건 조회] 게시글 아이디로 모든 댓글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "postingId", value = "게시글 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "댓글 관리")
    public Page<CommentDto> getAllCommentsByPostingId(@PathVariable Long postingId, @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return postingService.getAllCommentsByPostingId(postingId, page, size);
    }

    @PutMapping("/comments/{id}")
    @ApiOperation(value = "[수정] 댓글 수정")
    @ApiImplicitParam(name = "id", value = "댓글 아이디", paramType = "path", required = true)
    @Tag(name = "댓글 관리")
    public void modifyComment(@PathVariable Long id, @RequestBody ModifiedComment commentDto) {
        postingService.modifyComment(id, commentDto);
    }

    @DeleteMapping("/comments/{id}")
    @ApiOperation(value = "[삭제] 댓글 삭제")
    @ApiImplicitParam(name = "id", value = "댓글 아이디", paramType = "path", required = true)
    @Tag(name = "댓글 관리")
    public void removeComment(@PathVariable Long id) {
        postingService.removeComment(id);
    }

    @PostMapping("/comments")
    @ApiOperation(value = "[등록] 댓글 작성")
    @Tag(name = "댓글 관리")
    public void addComment(@RequestBody WrittenComment commentDto) {
        postingService.addComment(commentDto);
    }

    @GetMapping("/postings/user/{userId}")
    @ApiOperation(value = "[다건 조회] 특정 유저가 작성한 게시글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "유저별 게시글, 댓글 확인")
    public Page<PostListElem> getPostingsByUserId(@PathVariable String userId, @RequestParam("page") int page,
                                                  @RequestParam("size") int size) {
        return postingService.getPostingsByUserId(userId, page, size);
    }

    @GetMapping("/comments/user/{userId}")
    @ApiOperation(value = "[다건 조회] 특정 유저가 작성한 댓글 조회")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true)
    })
    @Tag(name = "유저별 게시글, 댓글 확인")
    public Page<CommentDto> getCommentsByUserId(@PathVariable String userId, @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return postingService.getCommentsByUserId(userId, page, size);
    }

}
