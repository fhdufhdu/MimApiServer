package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Role;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserFactory.class)
public @interface WithMockCustomUser {
    String username() default "fhdufhdu";

    Role[] roles() default Role.MEMBER;
}