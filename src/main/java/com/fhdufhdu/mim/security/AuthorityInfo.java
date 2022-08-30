package com.fhdufhdu.mim.security;

import org.springframework.security.core.GrantedAuthority;

public class AuthorityInfo implements GrantedAuthority {
    private String authority;

    public AuthorityInfo(String auth) {
        this.authority = auth;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
