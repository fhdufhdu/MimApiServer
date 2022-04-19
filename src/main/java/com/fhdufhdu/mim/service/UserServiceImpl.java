package com.fhdufhdu.mim.service;

import java.util.List;
import java.util.stream.Collectors;

import com.fhdufhdu.mim.dto.UserDto;
import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.DuplicateUserException;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundUserException;
import com.fhdufhdu.mim.repository.UserRepository;
import com.fhdufhdu.mim.service.util.UtilService;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl extends UtilService implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void login(String id, String pw) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        if (!passwordEncoder.matches(pw, user.getPw())) {
            throw new MismatchPasswdException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(NotFoundUserException::new);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPw())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public void signUp(UserDto user) {
        if (isDuplicated(user.getId())) {
            throw new DuplicateUserException();
        }
        user.setPw(passwordEncoder.encode(user.getPw()));
        userRepository.save(convertToDest(user, User.class));
    }

    private boolean isDuplicated(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserDto getUserInfo(String id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        UserDto userDto = convertToDest(user, UserDto.class);
        userDto.setPw(null);
        return userDto;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> userDtos = convertToDests(userRepository.findAll(), UserDto.class);
        return userDtos.stream().map(userDto -> {
            userDto.setPw(null);
            return userDto;
        }).collect(Collectors.toList());
    }

    @Override
    public void modifyUser(String id, UserDto userDto) {
        User original = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        if (userDto.getPw() != null)
            original.setPw(passwordEncoder.encode(userDto.getPw()));
        original.setNickName(userDto.getNickName());
        original.setProfilePath(userDto.getProfilePath());
        userRepository.save(original);
    }

    @Override
    public void withdrawal(String id) {
        User user = userRepository.findById(id).orElseThrow(NotFoundUserException::new);
        user.setIsRemoved(true);
        userRepository.save(user);
    }

}
