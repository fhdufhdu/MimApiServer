package com.fhdufhdu.mim.dto;

import java.security.Timestamp;

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
public class PostingDto {
    private Long id;
    private Long movieBoardId;
    private Integer commentCnt;
    private String userId;
    private String title;
    private String content;
    private Timestamp time;
    private Long postingNumber;
}
