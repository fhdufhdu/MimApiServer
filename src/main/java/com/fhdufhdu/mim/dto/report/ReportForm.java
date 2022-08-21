package com.fhdufhdu.mim.dto.report;

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
public class ReportForm {
    private String complainant;
    private String suspect;
    private String reportType;
    private String report_content;

    private Long postId;
}
