package com.fhdufhdu.mim.dto.report;

import java.sql.Timestamp;

import com.fhdufhdu.mim.dto.MovieDto;
import com.fhdufhdu.mim.dto.board.BoardDto;
import com.fhdufhdu.mim.dto.comment.CommentDto;
import com.fhdufhdu.mim.dto.post.PostingDto;

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
    @ApiModelProperty(example = "신고 시간")
    private Timestamp reportTimestamp;
    @ApiModelProperty(example = "댓글 정보")
    private CommentDto commentDto;
    @ApiModelProperty(example = "게시글 정보")
    private PostingDto postingDto;
    @ApiModelProperty(example = "게시판 정보")
    private BoardDto boardDto;
    @ApiModelProperty(example = "영화 정보")
    private MovieDto MovieDto;
}
