package com.fhdufhdu.mim.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class MovieRating {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String rating;

    @Builder.Default
    @OneToMany(mappedBy = "movieRating", fetch = FetchType.LAZY)
    private List<Movie> movies = new ArrayList<Movie>();
}
