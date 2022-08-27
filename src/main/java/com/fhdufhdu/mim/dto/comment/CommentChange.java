package com.fhdufhdu.mim.dto.comment;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentChange {
    @ApiModelProperty(example = "댓글 내용")
    private String content;
}
