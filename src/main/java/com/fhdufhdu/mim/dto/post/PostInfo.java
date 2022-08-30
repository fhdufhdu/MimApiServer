package com.fhdufhdu.mim.dto.post;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostInfo {
    @ApiModelProperty(example = "게시글 아이디")
    private Long id;
    @ApiModelProperty(example = "작성자 아이디")
    private String memberId;
    @ApiModelProperty(example = "게시글 제목")
    private String title;
    @ApiModelProperty(example = "게시글 내용")
    private String content;
    @ApiModelProperty(example = "게시글 작성 및 수정 시간")
    private Date time;
    private Integer commentCnt;
}
