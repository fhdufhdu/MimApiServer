package com.fhdufhdu.mim.mock;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

public class MockUtil {
    private static final ModelMapper modelMapper = new ModelMapper();
    public static <T> Page<T> convertListToPage(List<T> list, Pageable pageable){
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), list.size());
        Page<T> page = new PageImpl<>(list.subList(start,end), pageable, list.size());
        return page;
    }

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
}
