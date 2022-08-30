package com.fhdufhdu.mim.security;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fhdufhdu.mim.entity.Role;

public class User implements UserDetails {
    private List<AuthorityInfo> authorities;
    private String id;

    public User(String id, Role... authorities) {
        this.id = id;
        this.authorities = Arrays.stream(authorities)
                .map(a -> new AuthorityInfo(a.name()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return id != null ? true : false;
    }

}
