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
public class Genre {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String genreName;

    @Builder.Default
    @OneToMany(mappedBy = "genre", fetch = FetchType.LAZY)
    private List<MovieGenre> genres = new ArrayList<MovieGenre>();
}
