package com.fhdufhdu.mim.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

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
    private Integer releaseYear;
    private String synopsis;
    private String runningTime;
    private String movieRating;
    private String directors;
    private String actors;
    private String genres;
    private String features;
    private String dirName;

}
