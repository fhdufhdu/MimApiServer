package com.fhdufhdu.mim.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.MovieDtoV2;
import com.fhdufhdu.mim.dto.MovieLineDto;
import com.fhdufhdu.mim.dto.favoritemovie.FavoriteMovieAddDto;
import com.fhdufhdu.mim.service.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

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

    @GetMapping("/movies/titles")
    @ApiImplicitParam(name = "titles", value = "영화 제목들(리스트)", paramType = "query", required = true)
    @ApiOperation(value = "[다건 조회] 영화 제목 리스트로 영화 정보 조회(정확한 제목 필요)")
    @Tag(name = "영화 관리")
    public List<MovieDto> getMovieList(@RequestParam("titles") List<String> titles) {
        return searchService.getMovieList(titles);
    }

    // 이거 테스트 추가
    @GetMapping("/movies/ids")
    @ApiImplicitParam(name = "ids", value = "영화 아이디(리스트)", paramType = "query", required = true)
    @ApiOperation(value = "[다건 조회] 영화 아이디 리스트로 영화 정보 조회")
    @Tag(name = "영화 관리")
    public List<MovieDto> getMovieListByIds(@RequestParam("ids") List<Long> ids) {
        return searchService.getMovieListByIds(ids);
    }

    @GetMapping("/movies")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "title", value = "영화 제목", paramType = "query", required = true)
    })
    @ApiOperation(value = "[다건 조회] 영화 제목으로 영화 정보 조회(영화 제목의 일부만으로 가능)")
    @Tag(name = "영화 관리")
    public Page<MovieDto> getMovieList(@RequestParam("title") String title, int page, @RequestParam("size") int size) {
        return searchService.getMovieList(title, page, size);
    }

    @GetMapping("/movies/{id}/background")
    @ApiOperation(value = "[조회] 영화 아이디로 배경 이미지 가져오기")
    @ApiImplicitParam(name = "id", value = "영화아이디", paramType = "path", required = true)
    @Tag(name = "영화 관리")
    public void getMovieBackground(@ApiIgnore HttpServletResponse response, @PathVariable Long id) throws IOException {
        InputStream in = searchService.getMovieBackground(id);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
    }

    @GetMapping("/movies/{id}/poster")
    @ApiOperation(value = "[조회] 영화 아이디로 포스터 이미지 가져오기")
    @ApiImplicitParam(name = "id", value = "영화아이디", paramType = "path", required = true)
    @Tag(name = "영화 관리")
    public void getMoviePosting(@ApiIgnore HttpServletResponse response, @PathVariable Long id) throws IOException {
        InputStream in = searchService.getMoviePoster(id);
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        IOUtils.copy(in, response.getOutputStream());
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
    public List<MovieDto> searchByScean(@RequestParam("input") String input) {
        return searchService.searchByScean(input);
    }

    @GetMapping("/line")
    @ApiOperation(value = "대사 검색")
    @ApiImplicitParam(name = "input", value = "대사 서술", paramType = "query", required = true)
    @Tag(name = "대사 검색")
    public List<MovieLineDto> lineByScan(@RequestParam("input") String input) {
        return searchService.searchByLine(input);
    }

    @GetMapping("/favorite-movies/user/{userId}")
    @ApiOperation(value = "[다건 조회] 즐겨찾기한 영화 찾기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "size", value = "페이지 사이즈", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", value = "페이지 번호(0부터 시작)", paramType = "query", required = true),
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "path", required = true)
    })
    @Tag(name = "즐겨찾기(영화)")
    public Page<MovieDto> getMovieListByUserId(@PathVariable String userId, @RequestParam("page") int page,
            @RequestParam("size") int size) {
        return favoriteMovieService.getMovieListByUserId(userId, page, size);
    }

    @GetMapping("/favorite-movies/user/{userId}/movie/{movieId}")
    @ApiOperation(value = "[조회] 즐겨찾기한 영화 찾기")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "movieId", value = "영화 아이디", paramType = "path", required = true)
    })
    @Tag(name = "즐겨찾기(영화)")
    public boolean isFavoriteMovie(@PathVariable String userId, @PathVariable Long movieId) {
        return favoriteMovieService.isFavoriteMovie(userId, movieId);
    }

    @PostMapping("/favorite-movies")
    @ApiOperation(value = "[등록] 영화 즐겨찾기 하기")
    @Tag(name = "즐겨찾기(영화)")
    public void addFavorite(@RequestBody FavoriteMovieAddDto favoriteMovieDto) {
        favoriteMovieService.addFavorite(favoriteMovieDto);
    }

    @DeleteMapping("/favorite-movies/{id}")
    @ApiOperation(value = "[삭제] 즐겨찾기 아이디로 즐겨찾기한 영화 제거")
    @ApiImplicitParam(name = "id", value = "즐겨찾기 아이디", paramType = "path", required = true)
    @Tag(name = "즐겨찾기(영화)")
    public void removeFavorite(@PathVariable Long id) {
        favoriteMovieService.removeFavorite(id);
    }

    @DeleteMapping("/favorite-movies/user/{userId}/movie/{movieId}")
    @ApiOperation(value = "[삭제] 유저 아이디와 영화 아이디로 즐겨찾기한 영화 제거")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "유저 아이디", paramType = "path", required = true),
            @ApiImplicitParam(name = "movieId", value = "영화 아이디", paramType = "path", required = true)
    })
    @Tag(name = "즐겨찾기(영화)")
    public void removeFavoriteByUserIdAndMovieId(@PathVariable String userId, @PathVariable Long movieId) {
        favoriteMovieService.removeFavorite(userId, movieId);
    }
}