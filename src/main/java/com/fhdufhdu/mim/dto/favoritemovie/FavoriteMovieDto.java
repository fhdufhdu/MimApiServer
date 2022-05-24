package com.fhdufhdu.mim.dto.favoritemovie;

import java.sql.Timestamp;

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
public class FavoriteMovieDto {
    @ApiModelProperty(example = "즐겨찾기 아이디")
    private Long id;
    @ApiModelProperty(example = "유저 아이디")
    private String userId;
    @ApiModelProperty(example = "영화 아이디")
    private Long movieId;
    @ApiModelProperty(example = "등록 시간")
    private Timestamp time;
}
