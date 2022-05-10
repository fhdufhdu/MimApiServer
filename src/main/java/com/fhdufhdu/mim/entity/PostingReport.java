package com.fhdufhdu.mim.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class PostingReport {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posting_id")
    private Posting posting;

    @NotNull
    private String reportReason;

    @NotNull
    private Timestamp reporTimestamp;

    @NotNull
    @Column(columnDefinition = "boolean default false")
    private Boolean isConfirmed;
}
