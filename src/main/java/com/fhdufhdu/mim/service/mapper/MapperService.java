package com.fhdufhdu.mim.service.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public abstract class MapperService {
    @Autowired
    private ModelMapper modelMapper;

    protected <T, G> List<T> convertToDests(List<G> sources, Class<T> dest) {
        List<T> dtos = sources.stream().map(dto -> modelMapper.map(dto, dest))
                .collect(Collectors.toList());
        return dtos;
    }

    protected <T, G> T convertToDest(G source, Class<T> dest) {
        return modelMapper.map(source, dest);
    }

}
