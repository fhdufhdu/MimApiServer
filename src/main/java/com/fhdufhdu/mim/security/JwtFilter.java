package com.fhdufhdu.mim.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fhdufhdu.mim.exception.ExpiredTokenException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter implements Filter {
    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를
    // filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // getCookies()가 null을 반환한다면 로그인하지 않은 것, 또한, 리프레시 토큰으로 엑세스 토큰인척 한다면 바로 다음 필터
        // 체인으로 넘어가면 됨
        Optional<Cookie[]> cookies = Optional.ofNullable(httpRequest.getCookies());
        String token = cookies.map(c -> JwtManager.getTokenInCookies(c, JwtType.ACCESS)).orElse(null);

        if (token != null) {
            if (JwtManager.getType(token).equals(JwtType.REFRESH) || JwtManager.isExpired(token))
                throw new ExpiredTokenException();
            saveAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }

    // 엑세스 토큰 검증은 DB 접근 필요없기 때문에 커스텀해서 사용중
    public void saveAuthentication(String token) {
        Authentication auth = JwtManager.createAuth(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}