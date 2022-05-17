package com.fhdufhdu.mim.service.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.NotFoundBoardException;
import com.fhdufhdu.mim.security.CustomUser;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class UtilService {
    protected static final String AUTHORITY_PREFIX = "ROLE_";
    private final ModelMapper modelMapper = new ModelMapper();

    private CustomUser getAuthentication() {
        CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new NotFoundBoardException();
        }
        return user;
    }

    protected <T, G> List<T> convertToDests(List<G> sources, Class<T> dest) {
        List<T> dtos = sources.stream().map(dto -> modelMapper.map(dto, dest))
                .collect(Collectors.toList());
        return dtos;
    }

    protected <T, G> Page<T> convertToDests(Page<G> sources, Class<T> dest) {
        Page<T> dtos = sources.map(entity -> modelMapper.map(entity, dest));
        return dtos;
    }

    protected <T, G> T convertToDest(G source, Class<T> dest) {
        return modelMapper.map(source, dest);
    }

    protected Timestamp getNowTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    protected String getUserId() {
        CustomUser user = getAuthentication();
        return user.getUsername();
    }

    protected boolean hasAuthority(Role role) {
        CustomUser user = getAuthentication();
        return user.getAuthorities().stream()
                .anyMatch(x -> x.getAuthority().equals(AUTHORITY_PREFIX + role.name()));
    }

}
