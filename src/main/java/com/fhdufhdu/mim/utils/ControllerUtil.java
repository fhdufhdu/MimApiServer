package com.fhdufhdu.mim.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ControllerUtil {
    public static String getAuthSubject() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
