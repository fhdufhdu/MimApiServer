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
public class PostingReportSendDto {
    @ApiModelProperty(example = "게시글 아이디")
    private Long postingId;
    @ApiModelProperty(example = "게시글 신고 이유")
    private String reportReason;
}
