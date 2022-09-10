package com.fhdufhdu.mim.utils;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

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

}
