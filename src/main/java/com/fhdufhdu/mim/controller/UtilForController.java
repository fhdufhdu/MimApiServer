package com.fhdufhdu.mim.controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UtilForController {
    protected static final String AUTHORITY_PREFIX = "ROLE_";
    protected static final String AUTHORITY_ADMIN = AUTHORITY_PREFIX + "ADMIN";
    protected static final String AUTHORITY_USER = AUTHORITY_PREFIX + "USER";

    protected boolean checkAdminAuthority(UserDetails user) {
        return user.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals(AUTHORITY_ADMIN));
    }

    protected boolean checkUserAuthority(UserDetails user) {
        return user.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals(AUTHORITY_USER));
    }
}
