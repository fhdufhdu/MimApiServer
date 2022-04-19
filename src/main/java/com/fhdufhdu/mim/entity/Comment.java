package com.fhdufhdu.mim.entity;

import java.sql.Timestamp;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@NamedEntityGraphs({
                @NamedEntityGraph(name = "comment_user", attributeNodes = {
                                @NamedAttributeNode("user")
                })
})
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

        // @ManyToOne(fetch = FetchType.LAZY)
        // @JoinColumn(name = "parent_id")
        // private Comment parent;

        // @Builder.Default
        // @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
        // private List<Comment> childs = new ArrayList<>();

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
