package com.fhdufhdu.mim.mock;

import com.fhdufhdu.mim.entity.Report;
import com.fhdufhdu.mim.entity.ReportType;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class MockReportRepository {
    private List<Report> reports;

    public MockReportRepository(MockUserRepository userRepository, MockPostRepository postRepository){
        List<Integer> compliantIndexList = IndexListGenerator.generate(true);
        List<Integer> suspectIndexList = IndexListGenerator.generate(true);
        List<Integer> postIndexList = IndexListGenerator.generate(true);
        Random random = new Random();
        IntStream.range(0, 10).forEach(idx -> {
            if(compliantIndexList.get(idx) != suspectIndexList.get(idx)) return;

            int com = 0, sus = 0;
            while(com == sus){
                com = random.nextInt(10);
                sus = random.nextInt(10);
            }
            compliantIndexList.set(idx, com);
            suspectIndexList.set(idx, sus);
        });

        IntStream.range(0, 10).forEach(idx -> reports.add(Report.builder()
                        .id((long) idx)
                        .complainant(userRepository.getDataList().get(compliantIndexList.get(idx)))
                        .report_content("신고사유" + idx)
                        .reportType(idx % 2 == 0? ReportType.POST : ReportType.COMMENT)
                        .post(postRepository.getDataList().get(postIndexList.get(idx)))
                        .suspect(userRepository.getDataList().get(suspectIndexList.get(idx)))
                .build()));

    }
}
