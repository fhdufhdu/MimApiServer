package com.fhdufhdu.mim.dto.comment;

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
public class CommentAddDto {
    @ApiModelProperty(example = "게시글 아이디")
    private Long postingId;
    @ApiModelProperty(example = "댓글 그룹(대댓글 구분용도, 대댓글이면 대댓글의 ID, 댓글이면 안보내도 됌)")
    private Long commentGroup;
    @ApiModelProperty(example = "댓글 깊이(0 = 댓글, 1 = 대댓글)")
    private Integer depth;
    @ApiModelProperty(example = "댓글 내용")
    private String content;
}
