package com.fhdufhdu.mim.dto.member;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberInfo {
    private String id;
    private String nickname;
    private String role;
    private Date banEndDate;
}
