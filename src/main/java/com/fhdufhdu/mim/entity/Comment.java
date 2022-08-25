package com.fhdufhdu.mim.entity;

import java.sql.Timestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
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
public class Comment {
        @Id
        @GeneratedValue
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @NotNull
        @JoinColumn(name = "posting_id")
        private Post post;

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

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Info {
                @ApiModelProperty(example = "댓글 아이디")
                private Long id;
                @ApiModelProperty(example = "게시글 아이디")
                private Long postId;
                @ApiModelProperty(example = "작성자 아이디")
                private String userId;
                @ApiModelProperty(example = "댓글 그룹(대댓글 구분용도)")
                private Long commentGroup;
                @ApiModelProperty(example = "댓글 깊이(0 = 댓글, 1 = 대댓글)")
                private Integer depth;
                @ApiModelProperty(example = "댓글 내용")
                private String content;
                @ApiModelProperty(example = "작성 시간")
                private Timestamp time;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Change {
                @ApiModelProperty(example = "댓글 내용")
                private String content;
        }

        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @Builder
        public static class Writing {
                @ApiModelProperty(example = "게시글 아이디")
                private Long postId;
                @ApiModelProperty(example = "작성자 아이디")
                private String userId;
                @ApiModelProperty(example = "댓글 내용")
                private String content;
                private Long commentGroup;
                private Integer depth;
        }
}
