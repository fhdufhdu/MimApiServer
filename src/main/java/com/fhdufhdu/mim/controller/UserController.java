package com.fhdufhdu.mim.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fhdufhdu.mim.dto.UserDto;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.security.JwtTokenProvider;
import com.fhdufhdu.mim.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto user, HttpServletRequest request,
            HttpServletResponse response) {
        userService.login(user.getId(), user.getPw());

        // 로그인 토큰 발급
        response.setHeader(JwtTokenProvider.ACCESS_HEADER, jwtTokenProvider.createAccessToken(user.getId(),
                Arrays.asList(Role.ADMIN.name()), request.getRemoteAddr()));
        response.setHeader(JwtTokenProvider.REFRESH_HEADER, jwtTokenProvider.createRefreshToken(user.getId(),
                Arrays.asList(Role.ADMIN.name()), request.getRemoteAddr()));

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody UserDto user) {
        userService.signUp(user);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto getUserInfo(@PathVariable String id) {
        return userService.getUserInfo(id);
    }

    @GetMapping
    public List<UserDto> getAllUser() {
        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public void modifyUser(@PathVariable String id, @RequestBody UserDto userDto) {
        userService.modifyUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void withdrawal(@PathVariable String id) {
        userService.withdrawal(id);
    }

}
