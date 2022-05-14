package com.fhdufhdu.mim.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    public static String CLAIMES_KEY_ROLES = "roles";
    public static String CLAIMES_KEY_IP = "ip";
    public static String ACCESS_HEADER = "X-ACCESS-TOKEN";
    public static String REFRESH_HEADER = "X-REFRESH-TOKEN";

    @Value("{secret_key}")
    private String SECRET_KEY;

    // private long accessTokenValidMilisecond = 1000L * 60 * 30; // 30분
    // private long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24; // 1일
    private long accessTokenValidMilisecond = 1000L * 60 * 60 * 24 * 365; // 30분
    private long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24 * 365; // 1일

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    // 탈취되었을 때를 가정해 IP를 추가적으로 수집함
    public String createAccessToken(String userId, List<String> roles, String ip) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(CLAIMES_KEY_ROLES, roles);
        claims.put(CLAIMES_KEY_IP, ip);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + accessTokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256,
                        SECRET_KEY) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    public String createRefreshToken(String userId, List<String> roles, String ip) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(CLAIMES_KEY_ROLES, roles);
        claims.put(CLAIMES_KEY_IP, ip);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + refreshTokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256,
                        SECRET_KEY) // 암호화 알고리즘, secret값 세팅
                .compact();
    }

    public Claims getClaimFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    // 토큰으로 인증 정보 생성
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getuserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 id 추출
    private String getuserId(String token) {
        return getClaimFromToken(token).getSubject();
    }

    // Request의 Header에서 token 파싱 : "X-AUTH-TOKEN: jwt토큰"
    public String resolveAccessToken(HttpServletRequest req) {
        return req.getHeader(ACCESS_HEADER);
    }

    public String resolveRefreshToken(HttpServletRequest req) {
        return req.getHeader(REFRESH_HEADER);
    }

    // 토큰의 만료일자 확인
    public boolean validateTime(String token) {
        try {
            Claims claims = getClaimFromToken(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰의 ip 일치 여부 확인
    public boolean validateIp(String token, String ip) {
        try {
            Claims claims = getClaimFromToken(token);
            return claims.get(CLAIMES_KEY_IP).equals(ip);
        } catch (Exception e) {
            return false;
        }
    }
}