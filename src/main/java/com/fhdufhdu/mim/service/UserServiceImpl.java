package com.fhdufhdu.mim.service;

import java.util.List;

import com.fhdufhdu.mim.entity.User;
import com.fhdufhdu.mim.exception.DuplicateUserException;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NoFoundUserException;
import com.fhdufhdu.mim.repository.UserRepository;

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
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void login(String id, String pw) {
        User user = getUserInfo(id);
        if (!passwordEncoder.matches(pw, user.getPw())) {
            throw new MismatchPasswdException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username).orElseThrow(NoFoundUserException::new);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPw())
                .roles(user.getRole().name())
                .build();
    }

    @Override
    public void signUp(User user) {
        if (isDuplicated(user.getId())) {
            throw new DuplicateUserException();
        }
        user.setPw(passwordEncoder.encode(user.getPw()));
        userRepository.save(user);
    }

    private boolean isDuplicated(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public User getUserInfo(String id) {
        return userRepository.findById(id)
                .map(x -> x)
                .orElseThrow(() -> new NoFoundUserException());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void withdrawal(String id) {
        userRepository.deleteById(id);
    }

}
