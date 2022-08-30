package com.fhdufhdu.mim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fhdufhdu.mim.dto.report.ReportForm;
import com.fhdufhdu.mim.dto.report.ReportInfo;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Post;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.repository.PostRepository;
import com.fhdufhdu.mim.repository.ReportRepository;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {
    @InjectMocks
    private ReportService reportService;
    @Mock
    private ReportRepository reportRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void 신고_게시글신고() {
        final String content = "report";
        final String type = "POST";
        ReportForm form = ReportForm.builder()
                .reportContent(content)
                .reportType(type)
                .build();
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder().build()));
        when(postRepository.findById(any())).thenReturn(Optional.ofNullable(Post.builder().build()));
        when(reportRepository.save(any())).then(ivc -> ivc.getArgument(0));
        ReportInfo reportInfo = reportService.report(form);

        assertThat(reportInfo.getReportContent()).isEqualTo(content);
        assertThat(reportInfo.getReportType()).isEqualTo(type);
    }

}