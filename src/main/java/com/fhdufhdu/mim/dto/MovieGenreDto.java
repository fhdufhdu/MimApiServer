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
public class MovieGenreDto {
    @ApiModelProperty(example = "영화 장르 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 아이디")
    private Long movieId;
    @ApiModelProperty(example = "장르 아이디")
    private Long genreId;
}
