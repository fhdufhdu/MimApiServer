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

    @ManyToOne(fetch = FetchType.LAZY)
    private MovieRating movieRating;
    private String dirName;

    public void setMovieRating(MovieRating movieRating) {
        if (this.movieRating != null) {
            this.movieRating.getMovies().remove(this);
        }
        this.movieRating = movieRating;
        this.movieRating.getMovies().add(this);
    }
}
