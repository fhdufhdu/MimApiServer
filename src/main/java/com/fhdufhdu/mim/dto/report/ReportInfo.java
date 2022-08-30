package com.fhdufhdu.mim.dto.report;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date reportTime;
}
