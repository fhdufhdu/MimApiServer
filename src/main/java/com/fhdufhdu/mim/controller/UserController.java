package com.fhdufhdu.mim.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.security.JwtTokenProvider;
import com.fhdufhdu.mim.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping("/login")
    public String test1(@RequestBody User user, HttpServletRequest request,
            HttpServletResponse response) {
        userService.login(user.getId(), user.getPw());

        response.setHeader(JwtTokenProvider.ACCESS_HEADER, jwtTokenProvider.createAccessToken(user.getId(),
                Arrays.asList(Role.ADMIN.name()), request.getRemoteAddr()));
        response.setHeader(JwtTokenProvider.REFRESH_HEADER, jwtTokenProvider.createRefreshToken(user.getId(),
                Arrays.asList(Role.ADMIN.name()), request.getRemoteAddr()));

        return "Success";
    }

    @GetMapping("/sign-up")
    public String test2() {
        User user = User.builder()
                .id("fhdufhdu")
                .pw("fhdufhdu")
                .role(Role.ADMIN).build();
        userService.signUp(user);
        return "test";
    }

    @GetMapping("/test")
    public String test3() {
        return "test";
    }

}
