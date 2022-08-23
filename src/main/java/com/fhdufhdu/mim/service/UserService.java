package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.entity.User;

import java.sql.Timestamp;

public interface UserService {
    //유저 제재 기능 추가 필요함

    /** POST /users/login */
    User.Info login(User.Login user);

    /** POST /users/sign-up */
    void signUp(User.SignUp user);

    /** GET /users/id/{id} */
    boolean checkId(String id);

    /** GET /users/nick-name/{nickName} */
    boolean checkNickName(String nickName);

    /** GET /users/{id} */
    User.Info getUserInfo(String id);

    /** PUT /users/{id}/nickname */
    void changeNickname(String id, String nickName);

    /** PUT /users/{id}/password */
    void changePassword(String id, String pw);

    void banUser(String id, Timestamp endDate);

    /** DELETE /users/{id} */
    void withdrawal(String id);
}
