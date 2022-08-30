package com.fhdufhdu.mim.dto.comment;

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
public class CommentInfo {
    @ApiModelProperty(example = "댓글 아이디")
    private Long id;
    @ApiModelProperty(example = "게시글 아이디")
    private Long postId;
    @ApiModelProperty(example = "작성자 아이디")
    private String memberId;
    @ApiModelProperty(example = "댓글 그룹(대댓글 구분용도)")
    private Long commentGroup;
    @ApiModelProperty(example = "댓글 깊이(0 = 댓글, 1 = 대댓글)")
    private Integer depth;
    @ApiModelProperty(example = "댓글 내용")
    private String content;
    @ApiModelProperty(example = "작성 시간")
    private Date time;
}
