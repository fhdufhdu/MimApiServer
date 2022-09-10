package com.fhdufhdu.mim.security;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import com.fhdufhdu.mim.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public class JwtManager {
    private static final String TYPE = "TYPE";
    private static final String ROLES = "ROLES";
    private static final long AT_VALID = 1000L * 60 * 30; // 30분
    private static final long RT_VALID = 1000L * 60 * 60 * 24; // 1일
    private static Key secretKey = null;

    public static Key getSecretKey() {
        if (secretKey == null) {
            String rawSecretKey = null;
            try (
                    FileReader fr = new FileReader("./.secret-key");
                    BufferedReader br = new BufferedReader(fr)) {
                rawSecretKey = br.readLine();
                String encodedKey = Base64.getEncoder().encodeToString(rawSecretKey.getBytes());
                byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
                secretKey = Keys.hmacShaKeyFor(keyBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return secretKey;
    }

    public static String getTokenInCookies(Cookie[] cookies, JwtType key) {
        Optional<Cookie> cookie = Arrays.stream(cookies).filter(c -> key.getType().equals(c.getName())).findFirst();
        return cookie.map(Cookie::getValue).orElse(null);
    }

    public static String issueAccessToken(String memberId, Role role) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(ROLES, role);
        claims.put(TYPE, JwtType.ACCESS.getType());
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + AT_VALID))
                .signWith(getSecretKey())
                .compact();
    }

    public static String issueRefreshToken(String memberId) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(TYPE, JwtType.REFRESH);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + RT_VALID))
                .signWith(getSecretKey())
                .compact();
    }

    private static Claims getClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build().parseClaimsJws(token).getBody();
    }

    public static boolean isExpired(String token) {
        Claims claims = getClaim(token);
        return claims.getExpiration().before(new Date());
    }

    public static String getMemberId(String token) {
        Claims claims = getClaim(token);
        return claims.getSubject();
    }

    public static JwtType getType(String token) {
        Claims claims = getClaim(token);
        return JwtType.getEnum((String) claims.get(TYPE));
    }

    public static Authentication createAuth(String token) {
        Claims claims = getClaim(token);
        UserDetails userDetails = new User(claims.getSubject(), Role.valueOf((String) claims.get(JwtManager.ROLES)));
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
    }

}