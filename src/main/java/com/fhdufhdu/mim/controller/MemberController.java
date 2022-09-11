package com.fhdufhdu.mim.controller;

import java.util.Arrays;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fhdufhdu.mim.dto.DateParam;
import com.fhdufhdu.mim.dto.member.ChangeMemberInfo;
import com.fhdufhdu.mim.dto.member.ChangePassword;
import com.fhdufhdu.mim.dto.member.Login;
import com.fhdufhdu.mim.dto.member.MemberInfo;
import com.fhdufhdu.mim.dto.member.SignUp;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.security.JwtManager;
import com.fhdufhdu.mim.security.JwtType;
import com.fhdufhdu.mim.service.AuthService;
import com.fhdufhdu.mim.service.MemberService;
import com.fhdufhdu.mim.utils.ControllerUtil;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Api(tags = { "로그인", "회원가입", "유저관리" })
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final AuthService authService;

    private Cookie makeJwtCookie(JwtType type, String data) {
        Cookie result = new Cookie(type.getType(), data);
        result.setHttpOnly(true);
        return result;
    }

    private void addCookies(HttpServletResponse response, Cookie... cookies) {
        Arrays.stream(cookies).forEach(response::addCookie);
    }

    @PostMapping("/login")
    public MemberInfo login(@RequestBody Login login,
            HttpServletRequest request,
            HttpServletResponse response) {
        MemberInfo loginedMember = memberService.login(login);

        String refreshToken = JwtManager.issueRefreshToken(loginedMember.getId());
        String accessToken = JwtManager.issueAccessToken(loginedMember.getId(), Role.valueOf(loginedMember.getRole()));
        // 로그인 토큰 발급
        Cookie refreshCookie = makeJwtCookie(JwtType.REFRESH, refreshToken);
        Cookie accessCookie = makeJwtCookie(JwtType.ACCESS, accessToken);

        addCookies(response, refreshCookie, accessCookie);

        authService.saveTokens(loginedMember.getId(), refreshToken, accessToken);
        return loginedMember;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public MemberInfo signUP(@RequestBody SignUp signUp) {
        return memberService.signUp(signUp);
    }

    @GetMapping("/members/id/{id}")
    public boolean existId(@PathVariable("id") String id) {
        return memberService.existId(id);
    }

    @GetMapping("/members/nickname/{nickname}")
    public boolean existNickname(@PathVariable("id") String nickname) {
        return memberService.existNickname(nickname);
    }

    @PutMapping("/members/{id}/password")
    public void changePassword(@PathVariable("id") String id, @RequestBody ChangePassword cp,
            @AuthenticationPrincipal String user) {
        memberService.changePassword(id, cp, ControllerUtil.getAuthSubject());
    }

    @PutMapping("/members/{id}/nickname")
    public MemberInfo changeNickname(@PathVariable("id") String id, @RequestBody ChangeMemberInfo info) {
        return memberService.changeMemberInfo(id, info, ControllerUtil.getAuthSubject());
    }

    @PutMapping("/members/{id}/ban")
    public MemberInfo banUser(@PathVariable("id") String id, @RequestBody DateParam dateParam) {
        return memberService.banUser(id, dateParam);
    }

    @DeleteMapping("/members/{id}")
    public boolean withdrawal(@PathVariable("id") String id) {
        return memberService.withdrawal(id, ControllerUtil.getAuthSubject());
    }

    @GetMapping(value = "/refresh")
    public void refresh(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = JwtManager.getTokenInCookies(request.getCookies(), JwtType.ACCESS);
        String refreshToken = JwtManager.getTokenInCookies(request.getCookies(), JwtType.REFRESH);

        Map<JwtType, String> newTokens = authService.requestNewToken(refreshToken, accessToken);

        Cookie refreshCookie = makeJwtCookie(JwtType.REFRESH, newTokens.get(JwtType.REFRESH));
        Cookie accessCookie = makeJwtCookie(JwtType.ACCESS, newTokens.get(JwtType.ACCESS));

        addCookies(response, refreshCookie, accessCookie);
    }

}
