package com.fhdufhdu.mim.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Report {
    @Id
    @GeneratedValue
    private Long id;

    // 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "complainant_id")
    private User complainant;

    // 피 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suspect_id")
    private User suspect;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ReportType reportType;

    @NotNull
    private String report_content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
