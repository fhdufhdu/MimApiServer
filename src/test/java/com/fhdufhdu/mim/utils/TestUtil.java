package com.fhdufhdu.mim.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fhdufhdu.mim.entity.Role;

public class TestUtil {
    private static void setAuth(String username, Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public static void getMemberAuth(String username) {
        setAuth(username, Role.MEMBER);
    }

    public static void getBannedAuth(String username) {
        setAuth(username, Role.BANNED_MEMBER);
    }

    public static void getAdminAuth(String username) {
        setAuth(username, Role.ADMIN);
    }
}
