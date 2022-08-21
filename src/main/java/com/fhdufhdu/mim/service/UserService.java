package com.fhdufhdu.mim.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fhdufhdu.mim.dto.user.UserInfoDto;
import com.fhdufhdu.mim.dto.user.UserSignUpDto;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    //유저 제재 기능 추가 필요함

    /** POST /users/login */
    UserInfoDto login(String id, String pw);

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

    /** POST /users/{id}/profile */
    void saveProfile(String id, MultipartFile file) throws IOException;

    /** DELETE /users{id}/profile */
    void deleteProfile(String id);

    /**
     * GET /users{id}/profile
     * 
     * @throws FileNotFoundException
     */
    InputStream getUserProfile(String id) throws FileNotFoundException;
}
