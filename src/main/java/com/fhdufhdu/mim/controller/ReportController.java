package com.fhdufhdu.mim.controller;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.report.ReportForm;
import com.fhdufhdu.mim.dto.report.ReportInfo;
import com.fhdufhdu.mim.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/reports")
    public ReportInfo report(@RequestBody ReportForm report) {
        return reportService.report(report);
    }

    @GetMapping("/reports")
    public Page<ReportInfo> getAllReports(@ModelAttribute PageParam pageParam) {
        return reportService.getAllReports(pageParam);
    }
}
