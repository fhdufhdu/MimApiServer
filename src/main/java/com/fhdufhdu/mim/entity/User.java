package com.fhdufhdu.mim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User{
    @Id
    private String id;
    @NotNull
    private String pw;
    @NotNull
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private Timestamp banEndDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Info{
        private String id;
        private String nickname;
        private String role;
        private String banEndDate;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class Login{
        private String id;
        private String pw;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    public static class SignUp{
        private String id;
        private String pw;
        private String nickname;
    }
}
