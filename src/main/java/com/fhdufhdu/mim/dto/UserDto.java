package com.fhdufhdu.mim.dto;

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
public class UserDto {
    @ApiModelProperty(example = "유저 아이디")
    private String id;
    @ApiModelProperty(example = "사용자 비밀번호(해싱해서 보내줘야함)")
    private String pw;
    @ApiModelProperty(example = "닉네임")
    private String nickName;
    @ApiModelProperty(example = "프로필 사진 경로")
    private String profilePath;
    @ApiModelProperty(example = "역할(ADMIN, USER 둘 중 하나)")
    private String role;
    @ApiModelProperty(example = "삭제여부")
    private Boolean isRemoved;
}
