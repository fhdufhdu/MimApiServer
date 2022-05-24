package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.user.UserDto;
import com.fhdufhdu.mim.dto.user.UserInfoDto;
import com.fhdufhdu.mim.dto.user.UserSignUpDto;

public interface UserService {
    /** POST /users/login */
    UserDto login(String id, String pw);

    /** POST /users/sign-up */
    void signUp(UserSignUpDto user);

    /** GET /users/id/{id} */
    boolean checkId(String id);

    /** GET /users/nick-name/{nickName} */
    boolean checkNickName(String nickName);

    /** GET /users/{id} */
    UserInfoDto getUserInfo(String id);

    /** GET /users */
    List<UserInfoDto> getAllUsers();

    /** PUT /users/{id} */
    void modifyUser(String id, UserInfoDto userDto);

    /** DELETE /users/{id} */
    void withdrawal(String id);
}
