package com.fhdufhdu.mim.dto.movie;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieInfo {
    @ApiModelProperty(example = "영화 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 타이틀")
    private String title;
    @ApiModelProperty(example = "영화 영어 타이틀")
    private String engTitle;
    @ApiModelProperty(example = "영화 개봉연도")
    private Integer releaseYear;
    @ApiModelProperty(example = "영화 줄거리")
    private String synopsis;
    @ApiModelProperty(example = "영화 상영시간")
    private String runningTime;
    private String movieRating;
    private String directors;
    private String actors;
    private String genres;
    private String features;
    @ApiModelProperty(example = "영화 이미지 저장된 폴더")
    private String dirName;
}
