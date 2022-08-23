package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.entity.Report;
import org.springframework.data.domain.Page;

public interface ReportService {
    /** POST /reports */
    void report(Report.Form report);

    /** GET /reports */
    Page<Report.Info> getAllReports(PageParam pageParam);
}
