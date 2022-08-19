package com.fhdufhdu.mim.dto.post;

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
public class PostingAddDto {
    @ApiModelProperty(example = "게시판 아이디")
    private Long movieBoardId;
    @ApiModelProperty(example = "작성자 아이디")
    private String userId;
    @ApiModelProperty(example = "게시글 제목")
    private String title;
    @ApiModelProperty(example = "게시글 내용")
    private String content;
}
