package com.fhdufhdu.mim.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fhdufhdu.mim.dto.UserDto;
import com.fhdufhdu.mim.entity.Role;
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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UtilForController util;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user, HttpServletRequest request,
            HttpServletResponse response) {
        user = userService.login(user.getId(), user.getPw());

        // 로그인 토큰 발급
        response.setHeader(JwtTokenProvider.ACCESS_HEADER, jwtTokenProvider.createAccessToken(user.getId(),
                Arrays.asList(user.getRole()), request.getRemoteAddr()));
        response.setHeader(JwtTokenProvider.REFRESH_HEADER, jwtTokenProvider.createRefreshToken(user.getId(),
                Arrays.asList(Role.ADMIN.name()), request.getRemoteAddr()));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto user) {
        userService.signUp(user);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/users/{id}")
    public UserDto getUserInfo(@PathVariable String id, @AuthenticationPrincipal CustomUser user) {
        SecurityContextHolder.getContext().getAuthentication();
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        return userService.getUserInfo(id);
    }

    @GetMapping("/users")
    public List<UserDto> getAllUser() {
        return userService.getAllUsers();
    }

    @PutMapping("/users/{id}")
    public void modifyUser(@PathVariable String id, @RequestBody UserDto userDto,
            @AuthenticationPrincipal CustomUser user) {
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        userService.modifyUser(id, userDto);
    }

    @DeleteMapping("/users/{id}")
    public void withdrawal(@PathVariable String id, @AuthenticationPrincipal CustomUser user) {
        if (!user.getUsername().equals(id) && !util.checkAdminAuthority(user))
            throw new RuntimeException("test");
        userService.withdrawal(id);
    }

}
