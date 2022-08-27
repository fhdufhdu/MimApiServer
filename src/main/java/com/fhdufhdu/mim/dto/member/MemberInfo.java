package com.fhdufhdu.mim.dto.member;

import lombok.*;

import java.util.Date;

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
