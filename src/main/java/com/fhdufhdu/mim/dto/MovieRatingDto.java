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
public class MovieRatingDto {
    @ApiModelProperty(example = "영화 관람등급 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 관람등급 내용")
    private String rating;
}
