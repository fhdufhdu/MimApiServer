package com.fhdufhdu.mim.dto.favoritemovie;

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
public class FavoriteMovieAddDto {
    @ApiModelProperty(example = "유저 아이디")
    private String userId;
    @ApiModelProperty(example = "영화 아이디")
    private Long movieId;
}
