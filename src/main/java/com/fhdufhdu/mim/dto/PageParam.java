package com.fhdufhdu.mim.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageParam {
    @ApiModelProperty(
            value = "조회하고자 하는 페이지 번호. 페이지 번호는 1부터 시작한다",
            name = "페이지 번호",
            dataType = "int",
            example = "1")
    private int page;

    @ApiModelProperty(
            value = "한 페이지당 보여지는 요소의 갯수",
            name = "요소 갯수",
            dataType = "int",
            example = "10")
    private int size;
}
