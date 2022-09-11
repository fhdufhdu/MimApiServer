package com.fhdufhdu.mim.utils;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.WrongPermissionException;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServiceUtil {
    public static final String AUTHORITY_PREFIX = "ROLE_";
    private static final ModelMapper modelMapper = new ModelMapper();

    public static <T, G> List<T> convertToDest(List<G> sources, Class<T> dest) {
        List<T> dtos = sources.stream().map(dto -> modelMapper.map(dto, dest))
                .collect(Collectors.toList());
        return dtos;
    }

    public static <T, G> Page<T> convertToDest(Page<G> sources, Class<T> dest) {
        Page<T> dtos = sources.map(entity -> modelMapper.map(entity, dest));
        return dtos;
    }

    public static <T, G> T convertToDest(G source, Class<T> dest) {
        return modelMapper.map(source, dest);
    }

    public static Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static void checkAdminMember(String memberId) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(Role.ADMIN.name()));

        if (!isAdmin && !principal.equals(memberId))
            throw new WrongPermissionException();
    }
}
