package com.fhdufhdu.mim.controller;

import java.util.List;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.dto.favoritemovie.FavoriteMovieAddDto;
import com.fhdufhdu.mim.service.FavoriteMovieService;
import com.fhdufhdu.mim.service.SearchService;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Api(tags = { "영화 관리", "장면 검색", "대사 검색", "즐겨찾기(영화)" })
public class SearchController {
    private final SearchService searchService;
    private final FavoriteMovieService favoriteMovieService;

    @GetMapping("/movies/{id}")
    @ApiOperation(value = "[단건 조회] 아이디로 영화 조회")
    @ApiImplicitParam(name = "id", value = "영화아이디", paramType = "path", required = true)
    @Tag(name = "영화 관리")
    public MovieDtoV2 getMovieById(@PathVariable Long id) {
        return searchService.getMovieById(id);
    }

    @GetMapping("/movies/{id}/image")
    public void getMovieImage(@PathVariable Long id) {

    }

    @GetMapping("/movies/titles")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "titles", value = "영화 제목들(리스트)", paramType = "query", required = true)
    })
    @ApiOperation(value = "[다건 조회] 영화 제목 리스트로 영화 정보 조회(정확한 제목 필요)")
    @Tag(name = "영화 관리")
    public Page<MovieDto> getMovieList(@RequestParam("titles") List<String> titles, int page) {
        return searchService.getMovieList(titles, page);
    }

    @GetMapping("/movies")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "영화 제목", paramType = "query", required = true)
    })
    @ApiOperation(value = "[다건 조회] 영화 제목으로 영화 정보 조회(영화 제목의 일부만으로 가능)")
    @Tag(name = "영화 관리")
    public Page<MovieDto> getMovieList(@RequestParam("title") String title, int page) {
        return searchService.getMovieList(title, page);
    }

    // 사용하지 않을 예정
    @DeleteMapping("/movies/{movieId}")
    @ApiOperation(value = "굳이 사용할 필요 없어보임")
    @Tag(name = "영화 관리")
    public void removeMovie(@PathVariable Long movieId) {
        searchService.removeMovie(movieId);
    }

    @PutMapping("/movies/{movieId}")
    @ApiOperation(value = "[수정] 영화 정보 변경")
    @ApiImplicitParam(name = "movieId", value = "영화 아이디", paramType = "path", required = true)
    @Tag(name = "영화 관리")
    public void changeMovieInfo(@PathVariable Long movieId, @RequestBody MovieDtoV2 movie) {
        searchService.changeMovieInfo(movieId, movie);
    }

    @PostMapping("/movies")
    @ApiOperation(value = "[등록] 영화 추가")
    @Tag(name = "영화 관리")
    public void addMovie(@RequestBody MovieDtoV2 movie) {
        searchService.addMovie(movie);
    }

    @GetMapping("/scean")
    @ApiOperation(value = "장면 검색")
    @ApiImplicitParam(name = "input", value = "장면 서술", paramType = "query", required = true)
    @Tag(name = "장면 검색")
    public String searchByScan(@RequestParam("input") String input) {
        return "미완성";
    }

    @GetMapping("/line")
    @ApiOperation(value = "대사 검색")
    @ApiImplicitParam(name = "input", value = "대사 서술", paramType = "query", required = true)
    @Tag(name = "대사 검색")
    public String lineByScan(@RequestParam("input") String input) {
        return "미완성";
    }

    @GetMapping("/favorite-movies/user/{userId}")
    @ApiOperation(value = "[다건 조회] 즐겨찾기한 영화 찾기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "query", required = true)
    })
    @Tag(name = "즐겨찾기(영화)")
    public Page<MovieDto> getMovieListByUserId(@PathVariable String userId, @RequestParam("page") int page) {
        return favoriteMovieService.getMovieListByUserId(userId, page);
    }

    @PostMapping("/favorite-movies")
    @ApiOperation(value = "[등록] 영화 즐겨찾기 하기")
    @Tag(name = "즐겨찾기(영화)")
    public void addFavorite(@RequestBody FavoriteMovieAddDto favoriteMovieDto) {
        favoriteMovieService.addFavorite(favoriteMovieDto);
    }

    @DeleteMapping("/favorite-movies/{id}")
    @ApiOperation(value = "[삭제] 즐겨찾기한 영화 제거")
    @Tag(name = "즐겨찾기(영화)")
    public void removeFavorite(@PathVariable Long id) {
        favoriteMovieService.removeFavorite(id);
    }

}