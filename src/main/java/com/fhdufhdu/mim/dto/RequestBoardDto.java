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
public class RequestBoardDto {
    @ApiModelProperty(example = "게시판 요청 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 아이디")
    private Long movieId;
    @ApiModelProperty(example = "요청 횟수")
    private Integer requestCnt;
}
