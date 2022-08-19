package com.fhdufhdu.mim.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
public class Movie {
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
}
