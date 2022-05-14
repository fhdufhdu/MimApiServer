package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.dto.UserDto;
import com.fhdufhdu.mim.entity.User;

public interface UserService {
    /** POST /users/login */
    UserDto login(String id, String pw);

    /** POST /users/sign-up */
    void signUp(UserDto user);

    /** GET /users/{id} */
    UserDto getUserInfo(String id);

    /** GET /users */
    List<UserDto> getAllUsers();

    /** PUT /users/{id} */
    void modifyUser(String id, UserDto userDto);

    /** DELETE /users/{id} */
    void withdrawal(String id);
}
