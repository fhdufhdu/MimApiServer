package com.fhdufhdu.mim.dto.member;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUp {
    private String id;
    private String pw;
    private String nickname;
}
