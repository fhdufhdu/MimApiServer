package com.fhdufhdu.mim.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReportForm {
    private String complainant;
    private String suspect;
    private String reportType;
    private String reportContent;
    private Long postId;
}
