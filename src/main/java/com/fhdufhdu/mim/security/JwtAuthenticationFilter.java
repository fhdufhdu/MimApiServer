package com.fhdufhdu.mim.security;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fhdufhdu.mim.exception.ExceptionResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;

    // Request로 들어오는 Jwt Token의 유효성을 검증(jwtTokenProvider.validateToken)하는 filter를
    // filterChain에 등록
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String accessToken = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken((HttpServletRequest) request);
        String ip = request.getRemoteAddr();

        try {
            if (accessToken != null && refreshToken != null) {
                if (jwtTokenProvider.validateTime(accessToken)) { // 엑세스 토큰 유효
                    if (jwtTokenProvider.validateIp(accessToken, ip)) { // ip 일치 여부(보안)
                        saveAuthentication(accessToken);
                    }
                } else if (jwtTokenProvider.validateTime(refreshToken)) { // 리프레시 토큰 유효
                    if (jwtTokenProvider.validateIp(refreshToken, ip)) { // ip 일치 여부(보안)
                        Claims accessClaims = jwtTokenProvider.getClaimFromToken(refreshToken); // 리프레시 토큰에서 클레임 추출
                        accessToken = jwtTokenProvider.createAccessToken(accessClaims.getSubject(),
                                (List<String>) accessClaims.get(JwtTokenProvider.CLAIMES_KEY_ROLES),
                                (String) accessClaims.get(JwtTokenProvider.CLAIMES_KEY_IP));
                        saveAuthentication(accessToken);
                    }
                }
                HttpServletResponse hServletResponse = (HttpServletResponse) response;
                hServletResponse.setHeader(JwtTokenProvider.ACCESS_HEADER, accessToken);
                hServletResponse.setHeader(JwtTokenProvider.REFRESH_HEADER, refreshToken);
                // (User)
                // SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
            }
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            setErrorResponse((HttpServletResponse) response, e);
        }

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(String token) {
        Authentication auth = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void setErrorResponse(HttpServletResponse response, Throwable ex) {
        ObjectMapper mapper = new ObjectMapper();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                ex.getMessage());
        try {
            String json = mapper.writeValueAsString(exceptionResponse);
            System.out.println(json);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}