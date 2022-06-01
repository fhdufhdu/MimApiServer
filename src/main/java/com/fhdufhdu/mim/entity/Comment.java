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
// @NamedEntityGraphs({
// @NamedEntityGraph(name = "comment_user", attributeNodes = {
// @NamedAttributeNode("user")
// }),
// @NamedEntityGraph(name = "comment_posting", attributeNodes = {
// @NamedAttributeNode("posting")
// })
// })
public class Comment {
        @Id
        @GeneratedValue
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @NotNull
        @JoinColumn(name = "posting_id")
        private Posting posting;

        @NotNull
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @NotNull
        private Long commentGroup;

        @NotNull
        private Integer depth;

        @NotNull
        private String content;

        @NotNull
        private Timestamp time;

        @NotNull
        @Column(columnDefinition = "boolean default false")
        private Boolean isRemoved;
}
