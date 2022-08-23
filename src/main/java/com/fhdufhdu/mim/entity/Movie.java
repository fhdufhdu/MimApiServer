package com.fhdufhdu.mim.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Movie extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;
    private String engTitle;
    private Integer year;
    private String synopsis;
    private String runningTime;
    private String movieRating;
    private String directors;
    private String actors;
    private String genres;
    private String features;
    private String dirName;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Info {
        @ApiModelProperty(example = "영화 아이디")
        private Long id;
        @ApiModelProperty(example = "영화 타이틀")
        private String title;
        @ApiModelProperty(example = "영화 영어 타이틀")
        private String engTitle;
        @ApiModelProperty(example = "영화 개봉연도")
        private Integer year;
        @ApiModelProperty(example = "영화 줄거리")
        private String synopsis;
        @ApiModelProperty(example = "영화 상영시간")
        private String runningTime;
        private String movieRating;
        private String directors;
        private String actors;
        private String genres;
        private String features;
        @ApiModelProperty(example = "영화 이미지 저장된 폴더")
        private String dirName;
    }

    public static class InfoWithLine {
        @ApiModelProperty(example = "영화 아이디")
        private Long id;
        @ApiModelProperty(example = "영화 타이틀")
        private String title;
        @ApiModelProperty(example = "영화 영어 타이틀")
        private String engTitle;
        @ApiModelProperty(example = "영화 개봉연도")
        private Integer year;
        @ApiModelProperty(example = "영화 줄거리")
        private String synopsis;
        @ApiModelProperty(example = "영화 상영시간")
        private String runningTime;
        private String movieRating;
        private String directors;
        private String actors;
        private String genres;
        private String features;
        private List lines;
        @ApiModelProperty(example = "영화 이미지 저장된 폴더")
        private String dirName;
    }
}
