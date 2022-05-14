package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.security.CustomUser;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserFactory implements WithSecurityContextFactory<WithMockCustomUser> {
    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
        SecurityContext context = SecurityContextHolder.getContext();

        UserDetails userDetails = CustomUser.builder()
                .username(customUser.username())
                .password("")
                .roles(rolesToNames(customUser.roles()))
                .build();
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());

        context.setAuthentication(token);
        return context;
    }

    private String[] rolesToNames(Role[] roles) {
        String[] result = new String[roles.length];
        for (int i = 0; i < roles.length; i++) {
            result[i] = roles[i].name();
        }
        return result;
    }
}
