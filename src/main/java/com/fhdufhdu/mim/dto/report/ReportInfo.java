package com.fhdufhdu.mim.dto.report;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReportInfo {
    private Long id;
    private String complainant;
    private String suspect;
    private String reportType;
    private String reportContent;
    private Long postId;
}
