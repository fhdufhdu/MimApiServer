package com.fhdufhdu.mim.dto;

import java.util.List;

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
public class MovieDtoV2 {
    private MovieDto movieDto;
    private List<String> directors;
    private List<String> actors;
    private List<String> writers;
    private List<String> genres;
    private List<String> features;
    private String rating;
}