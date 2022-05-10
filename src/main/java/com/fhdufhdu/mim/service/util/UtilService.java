package com.fhdufhdu.mim.service.util;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.entity.Role;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class UtilService {
    private final ModelMapper modelMapper = new ModelMapper();
    private Optional<Authentication> curAuthentication;

    public UtilService() {
        curAuthentication = Optional
                .ofNullable((Authentication) SecurityContextHolder.getContext().getAuthentication());
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
        return curAuthentication.map(auth -> ((User) auth.getPrincipal()).getUsername()).orElse(null);
    }

    protected boolean hasPermission(Role role) {
        return curAuthentication.map(auth -> {
            for (GrantedAuthority permission : ((User) auth.getPrincipal()).getAuthorities())
                if (permission.getAuthority().equals(role.name()))
                    return true;
            return false;
        }).orElse(false);
    }

}
