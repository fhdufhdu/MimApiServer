package com.fhdufhdu.mim.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHistory extends BaseEntity<Long> {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String search_text;

    @NotNull
    private Timestamp timestamp;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ListElem{
        private String search_text;
        private Timestamp timestamp;
    }
}
