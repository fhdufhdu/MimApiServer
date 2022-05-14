package com.fhdufhdu.mim.dto;

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
    private String id;
    private String pw;
    private String nickName;
    private String profilePath;
    private String role;
    private Boolean isRemoved;
}
