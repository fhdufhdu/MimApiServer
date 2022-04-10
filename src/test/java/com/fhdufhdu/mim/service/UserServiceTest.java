package com.fhdufhdu.mim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import com.fhdufhdu.mim.dto.UserDto;
import com.fhdufhdu.mim.entity.Role;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@Transactional
public class UserServiceTest {
    @Autowired
    UserService userService;

    @BeforeEach
    public void 테스트계정추가() {
        UserDto admin = UserDto.builder().id("admin").pw("adminPw").role(Role.ADMIN.name()).build();
        UserDto user = UserDto.builder().id("user").pw("userPw").role(Role.USER.name()).build();

        userService.signUp(admin);
        userService.signUp(user);
    }

    @AfterEach
    public void 유저삭제() {
        userService.withdrawal("user");
        userService.withdrawal("admin");
        try {
            userService.getUserInfo("user");
            fail("삭제 실패");
        } catch (Exception e) {
            log.info("삭제 성공");
        }
    }

    @Test
    public void 로그인() {
        userService.login("user", "userPw");
    }

    @Test
    public void 로그인실패() {
        try {
            userService.login("user", "adminPw");
            fail("로그인 실패해야 하지만 성공");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Test
    public void 유저불러오기_수정() {
        UserDto user = userService.getUserInfo("user");
        user.setNickName("testNickName");
        userService.modifyUser(user.getId(), user);
        UserDto modifiedUser = userService.getUserInfo("user");
        assertThat(modifiedUser.getNickName()).isEqualTo("testNickName");
    }

    @Test
    public void 모든유저불러오기() {
        List<UserDto> user = userService.getAllUsers();
        assertThat(user.size()).isEqualTo(2);
    }
}