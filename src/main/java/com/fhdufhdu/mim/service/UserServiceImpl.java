package com.fhdufhdu.mim.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.dto.user.UserDto;
import com.fhdufhdu.mim.dto.user.UserInfoDto;
import com.fhdufhdu.mim.dto.user.UserSignUpDto;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.DuplicateUserException;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundProfilePathException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.security.CustomUser;
import com.fhdufhdu.mim.service.util.UtilService;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl extends UtilService implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto login(String id, String pw) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        if (!passwordEncoder.matches(pw, user.getPw())) {
            throw new MismatchPasswdException();
        }
        return convertToDest(user, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(NotFoundUserException::new);
        return CustomUser.builder()
                .username(user.getId())
                .password(user.getPw())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public void signUp(UserSignUpDto user) {
        if (checkId(user.getId())) {
            throw new DuplicateUserException();
        }
        user.setPw(passwordEncoder.encode(user.getPw()));
        User saveUser = convertToDest(user, User.class);
        saveUser.setRole(Role.USER);
        saveUser.setIsRemoved(false);
        saveUser.setNickName(user.getNickName());
        userRepository.save(saveUser);
    }

    @Override
    public boolean checkId(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean checkNickName(String nickName) {
        return userRepository.existsByNickName(nickName);
    }

    @Override
    public UserInfoDto getUserInfo(String id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        UserInfoDto userDto = convertToDest(user, UserInfoDto.class);
        userDto.setPw(null);
        return userDto;
    }

    @Override
    public List<UserInfoDto> getAllUsers() {
        List<UserInfoDto> userDtos = convertToDests(userRepository.findAll(), UserInfoDto.class);
        return userDtos.stream().map(userDto -> {
            userDto.setPw(null);
            return userDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void modifyUser(String id, UserInfoDto userDto) {
        User original = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        if (userDto.getPw() != null)
            original.setPw(passwordEncoder.encode(userDto.getPw()));
        original.setNickName(userDto.getNickName());
        userRepository.save(original);
    }

    @Override
    public void withdrawal(String id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        deleteUserProfile(user);
        user.setNickName(null);
        user.setIsRemoved(true);
        userRepository.save(user);
    }

    @Override
    public void saveProfile(String id, MultipartFile file) throws IOException {
        Date curDate = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyyMMddHHmmss_");
        String dateString = simpleFormat.format(curDate);

        // UUID 사용
        String uuid = UUID.randomUUID().toString();
        // 파일의 확장자 알아오기
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        // 파일명 생성하고 디비에 저장
        String filename = "/home/fhdufhdu/user_profile/" + dateString + uuid + "." + extension;

        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        deleteUserProfile(user);
        user.setProfilePath(filename);
        userRepository.save(user);
        // 파일 저장
        FileCopyUtils.copy(file.getBytes(), new File(filename));
    }

    @Override
    public void deleteProfile(String id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        deleteUserProfile(user);
        user.setProfilePath(null);
        userRepository.save(user);
    }

    private void deleteUserProfile(User user) {
        if (user.getProfilePath() != null) {
            File savedFile = new File(user.getProfilePath());
            if (savedFile.exists()) {
                savedFile.delete();
            }
        }
    }

    public InputStream getUserProfile(String id) throws FileNotFoundException {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        if (user.getProfilePath() == null) {
            throw new NotFoundProfilePathException();
        }
        return new FileInputStream(user.getProfilePath());
    }

}
