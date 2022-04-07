package com.fhdufhdu.mim.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private Long id;
    private String title;
    private String engTitle;
    private Integer year;
    private String synopsis;
    private String runningTime;
    private Long movieRatingId;
    private String dirName;
}
