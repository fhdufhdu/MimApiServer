package com.fhdufhdu.mim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@NamedEntityGraphs({
                @NamedEntityGraph(name = "board_movie", attributeNodes = {
                                @NamedAttributeNode("movie")
                })
})
public class Board {
        @Id
        @GeneratedValue
        private Long id;

        @NotNull
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "movie_id")
        private Movie movie;

        @NotNull
        private Long lastPostingNumber;

        @NotNull
        @Column(columnDefinition = "boolean default false")
        private Boolean isRemoved;
}
