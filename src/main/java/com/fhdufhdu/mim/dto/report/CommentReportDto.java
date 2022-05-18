package com.fhdufhdu.mim.dto.report;

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
public class CommentReportDto {
    @ApiModelProperty(example = "댓글 신고 아이디")
    private Long id;
    @ApiModelProperty(example = "댓글 아이디")
    private Long commentId;
    @ApiModelProperty(example = "신고 이유")
    private String reportReason;
}
