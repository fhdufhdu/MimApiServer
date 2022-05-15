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
public class MovieDtoV2 {
    @ApiModelProperty(example = "영화 기본 내용")
    private MovieDto movieDto;
    @ApiModelProperty(example = "감독 리스트")
    private List<String> directors;
    @ApiModelProperty(example = "연기자 리스트")
    private List<String> actors;
    @ApiModelProperty(example = "각본가 리스트")
    private List<String> writers;
    @ApiModelProperty(example = "장르 리스트")
    private List<String> genres;
    @ApiModelProperty(example = "특징 리스트")
    private List<String> features;
    @ApiModelProperty(example = "관람등급")
    private String rating;
}