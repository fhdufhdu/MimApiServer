package com.fhdufhdu.mim.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
@Entity
@Builder
public class User {
    @Id
    private String id;
    @NotNull
    private String pw;
    @NotNull
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    private Timestamp banEndDate;

    public User() {
        role = Role.USER;
    }
}
