package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.User;

public interface UserService {
    /** POST /users/login */
    void login(String id, String pw);

    /** POST /users */
    void signUp(User user);

    /** GET /users/{id} */
    User getUserInfo(String id);

    /** GET /users */
    List<User> getAllUsers();

    /** DELETE /users/{id} */
    void withdrawal(String id);
}
