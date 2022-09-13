package com.fhdufhdu.mim.aop;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fhdufhdu.mim.entity.Role;

@Aspect
@Component
public class AuthAdvisor {
    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint joinPoint, Class<T> clazz) {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        return ms.getMethod().getAnnotation(clazz);
    }

    private void setAuth(String username, Role role) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role.name()));
        Authentication auth = new UsernamePasswordAuthenticationToken(username, "", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Around("@annotaion(com.fhdufhdu.mim.MemberAuth)")
    public Object userAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        MemberAuth memberAuth = getAnnotation(joinPoint, MemberAuth.class);
        setAuth(memberAuth.name(), Role.MEMBER);
        Object result = joinPoint.proceed();
        return result;
    }

    @Around("@annotaion(com.fhdufhdu.mim.BannedMemberAuth)")
    public Object bannedMemberAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        BannedMemberAuth bannedMemberAuth = getAnnotation(joinPoint, BannedMemberAuth.class);
        setAuth(bannedMemberAuth.name(), Role.BANNED_MEMBER);
        Object result = joinPoint.proceed();
        return result;
    }

    @Around("@annotaion(com.fhdufhdu.mim.AdminAuth)")
    public Object adminAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        AdminAuth adminAuth = getAnnotation(joinPoint, AdminAuth.class);
        setAuth(adminAuth.name(), Role.ADMIN);
        Object result = joinPoint.proceed();
        return result;
    }
}
