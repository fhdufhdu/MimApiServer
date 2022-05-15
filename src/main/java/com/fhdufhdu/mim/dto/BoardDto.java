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
public class BoardDto {
    @ApiModelProperty(example = "게시판 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 아이디")
    private Long movieId;
    @ApiModelProperty(example = "영화 제목")
    private String movieName;
    @ApiModelProperty(example = "영화 사진 데이터")
    private String movieDir;
    @ApiModelProperty(example = "마지막 게시판 번호")
    private Long lastPostingNumber;
}
