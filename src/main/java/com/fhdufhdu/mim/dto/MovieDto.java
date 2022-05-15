package com.fhdufhdu.mim.dto;

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
public class MovieDto {
    @ApiModelProperty(example = "영화 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 타이틀")
    private String title;
    @ApiModelProperty(example = "영화 영어 타이틀")
    private String engTitle;
    @ApiModelProperty(example = "영화 개봉연도")
    private Integer year;
    @ApiModelProperty(example = "영화 줄거리")
    private String synopsis;
    @ApiModelProperty(example = "영화 상영시간")
    private String runningTime;
    @ApiModelProperty(example = "영화 관람등급 아이디")
    private Long movieRatingId;
    @ApiModelProperty(example = "영화 이미지 저장된 폴더")
    private String dirName;
}