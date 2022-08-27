package com.fhdufhdu.mim.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentWriting {
    @ApiModelProperty(example = "게시글 아이디")
    private Long postId;
    @ApiModelProperty(example = "작성자 아이디")
    private String memberId;
    @ApiModelProperty(example = "댓글 내용")
    private String content;
    private Long commentGroup;
    private Integer depth;
}
