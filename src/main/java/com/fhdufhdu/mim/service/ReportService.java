package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fhdufhdu.mim.dto.PageParam;
import com.fhdufhdu.mim.dto.report.ReportForm;
import com.fhdufhdu.mim.dto.report.ReportInfo;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.entity.Report;
import com.fhdufhdu.mim.entity.ReportType;
import com.fhdufhdu.mim.exception.NotFoundMemberException;
import com.fhdufhdu.mim.exception.NotFoundPostException;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.ReportRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * POST /reports
     * </p>
     * [service 테스트 항목]
     * <ol>
     * <li>신고자를 못 찾았을 때 예외가 발생하는지</li>
     * <li>피신고자를 못 찾았을 때 예외가 발생하는지</li>
     * <li>게시글을 못 찾았을 때 예외가 발생하는지</li>
     * <li>report.getReportType() -> ReportType, 신고가 제대로 되는지</li>
     * </ol>
     */
    public ReportInfo report(ReportForm report) {
        Member complainant = memberRepository.findById(report.getComplainant())
                .orElseThrow(NotFoundMemberException::new);
        Member suspect = memberRepository.findById(report.getSuspect()).orElseThrow(NotFoundMemberException::new);
        Post post = postRepository.findById(report.getPostId()).orElseThrow(NotFoundPostException::new);
        ReportType type = ReportType.valueOf(report.getReportType());
        Report newReport = Report.builder()
                .post(post)
                .reportType(type)
                .reportContent(report.getReportContent())
                .complainant(complainant)
                .suspect(suspect)
                .reportTime(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ServiceUtil.convertToDest(reportRepository.save(newReport), ReportInfo.class);
    }

    /**
     * GET /reports
     */
    public Page<ReportInfo> getAllReports(PageParam pageParam) {
        Page<Report> reports = reportRepository.findAll(pageParam.toPageRequest());
        return ServiceUtil.convertToDest(reports, ReportInfo.class);
    }
}
