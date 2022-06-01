package com.fhdufhdu.mim.dto.posting;

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
public class PostingDto {
    @ApiModelProperty(example = "게시글 아이디")
    private Long id;
    @ApiModelProperty(example = "게시판 아이디")
    private Long movieBoardId;
    @ApiModelProperty(example = "게시글에 달린 댓글 갯수")
    private Integer commentCnt;
    @ApiModelProperty(example = "작성자 아이디")
    private String userId;
    @ApiModelProperty(example = "게시글 제목")
    private String title;
    @ApiModelProperty(example = "게시글 내용")
    private String content;
    @ApiModelProperty(example = "게시글 작성 및 수정 시간")
    private Timestamp time;
    @ApiModelProperty(example = "게시글 순번")
    private Long postingNumber;
}
