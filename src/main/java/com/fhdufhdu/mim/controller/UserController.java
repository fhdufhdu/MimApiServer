package com.fhdufhdu.mim.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fhdufhdu.mim.dto.user.UserDto;
import com.fhdufhdu.mim.dto.user.UserInfoDto;
import com.fhdufhdu.mim.dto.user.UserLoginDto;
import com.fhdufhdu.mim.dto.user.UserSignUpDto;
import com.fhdufhdu.mim.security.CustomUser;
import com.fhdufhdu.mim.security.JwtTokenProvider;
import com.fhdufhdu.mim.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@Api(tags = { "로그인, 회원가입, 유저 관리 기능 API" })
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UtilForController util;

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<String> login(
            @RequestBody UserLoginDto user,
            HttpServletRequest request,
            HttpServletResponse response) {
        UserDto loginUser = userService.login(user.getId(), user.getPw());

        // 로그인 토큰 발급
        response.setHeader(JwtTokenProvider.ACCESS_HEADER, jwtTokenProvider.createAccessToken(user.getId(),
                Arrays.asList(loginUser.getRole()), request.getRemoteAddr()));
        response.setHeader(JwtTokenProvider.REFRESH_HEADER, jwtTokenProvider.createRefreshToken(user.getId(),
                Arrays.asList(loginUser.getRole()), request.getRemoteAddr()));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<String> signUp(@RequestBody UserSignUpDto user) {
        userService.signUp(user);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    @ApiOperation(value = "유저정보 가져오기", notes = "본인이거나 ADMIN 권한만 접근 가능")
    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
    public UserInfoDto getUserInfo(@PathVariable String id, @ApiIgnore @AuthenticationPrincipal CustomUser user) {
        SecurityContextHolder.getContext().getAuthentication();
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        return userService.getUserInfo(id);
    }

    @GetMapping("/users")
    @ApiOperation(value = "모든 유저정보 가져오기", notes = "ADMIN 권한만 접근 가능")
    public List<UserInfoDto> getAllUser() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    @ApiOperation(value = "유저정보 수정", notes = "본인이거나 ADMIN 권한만 접근 가능")
    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
    public void modifyUser(@PathVariable String id, @RequestBody UserInfoDto userDto,
            @ApiIgnore @AuthenticationPrincipal CustomUser user) {
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        userService.modifyUser(id, userDto);
    }

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "유저정보 삭제", notes = "본인이거나 ADMIN 권한만 접근 가능")
    @ApiImplicitParam(name = "id", value = "유저아이디", paramType = "path")
    public void withdrawal(@PathVariable String id, @ApiIgnore @AuthenticationPrincipal CustomUser user) {
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        userService.withdrawal(id);
    }

}
