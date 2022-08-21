package com.fhdufhdu.mim.dto.report;

import java.sql.Timestamp;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.board.BoardDto;

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
public class PostingReportDto {
    @ApiModelProperty(example = "게시글 신고 아이디")
    private Long id;
    @ApiModelProperty(example = "게시글 아이디")
    private Long postingId;
    @ApiModelProperty(example = "게시글 신고 이유")
    private String reportReason;
    @ApiModelProperty(example = "신고 시간")
    private Timestamp reportTimestamp;
    @ApiModelProperty(example = "게시글 정보")
    private PostingDto postingDto;
    @ApiModelProperty(example = "게시판 정보")
    private BoardDto boardDto;
    @ApiModelProperty(example = "영화 정보")
    private MovieDto MovieDto;
}
