package com.fhdufhdu.mim.dto.user;

import io.swagger.annotations.ApiModelProperty;
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
public class UserLoginDto {
    @ApiModelProperty(example = "유저 아이디")
    private String id;
    @ApiModelProperty(example = "사용자 비밀번호(해싱해서 보내줘야함)")
    private String pw;
}
