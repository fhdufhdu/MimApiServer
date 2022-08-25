package com.fhdufhdu.mim.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
public class Post{
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private Timestamp time;

    @NotNull
    private Integer commentCnt;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Info{
        @ApiModelProperty(example = "게시글 아이디")
        private Long id;
        @ApiModelProperty(example = "작성자 아이디")
        private String userId;
        @ApiModelProperty(example = "게시글 제목")
        private String title;
        @ApiModelProperty(example = "게시글 내용")
        private String content;
        @ApiModelProperty(example = "게시글 작성 및 수정 시간")
        private Timestamp time;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class ListElem{
        @ApiModelProperty(example = "게시글 아이디")
        private Long id;
        @ApiModelProperty(example = "작성자 아이디")
        private String userId;
        @ApiModelProperty(example = "게시글 제목")
        private String title;
        @ApiModelProperty(example = "게시글 작성 및 수정 시간")
        private Timestamp time;
        @ApiModelProperty(example = "게시글에 달린 댓글 갯수")
        private Integer commentCnt;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Change {
        @ApiModelProperty(example = "게시글 제목")
        private String title;
        @ApiModelProperty(example = "게시글 내용")
        private String content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Writing {
        @ApiModelProperty(example = "작성자 아이디")
        private String userId;
        @ApiModelProperty(example = "게시글 제목")
        private String title;
        @ApiModelProperty(example = "게시글 내용")
        private String content;
    }
}
