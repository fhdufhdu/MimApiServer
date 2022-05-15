package com.fhdufhdu.mim.dto.worker;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WorkerDto {
    @ApiModelProperty(example = "영화 종사자 아이디")
    private Long id;
    @ApiModelProperty(example = "영화 종사자 이름")
    private String name;
}
