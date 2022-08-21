package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.report.PostingReportDto;

import com.fhdufhdu.mim.dto.report.ReportForm;
import org.springframework.data.domain.Page;

public interface ReportService {
    /** POST /reports */
    void report(ReportForm report);

    /** GET /reports */
    Page<PostingReportDto> getAllReports(PageParam pageParam);
}
