package com.fhdufhdu.mim.dto;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieLineDto {
    @ApiModelProperty(example = "영화 기본 내용")
    private MovieDto movieDto;
    @ApiModelProperty(example = "대사 리스트")
    private List<String> subtitles;
}