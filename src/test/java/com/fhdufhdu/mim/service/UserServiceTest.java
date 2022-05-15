// package com.fhdufhdu.mim.service;

// import static org.junit.jupiter.api.Assertions.fail;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.BDDMockito.*;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.assertj.core.api.Assertions.*;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// import com.fhdufhdu.mim.dto.UserDto;
// import com.fhdufhdu.mim.entity.User;
// import com.fhdufhdu.mim.exception.DuplicateUserException;
// import com.fhdufhdu.mim.exception.MismatchPasswdException;
// import com.fhdufhdu.mim.exception.NotFoundUserException;
// import com.fhdufhdu.mim.repository.UserRepository;
// import com.fhdufhdu.mim.security.CustomDelegatingPasswordEncoder;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import lombok.extern.slf4j.Slf4j;

// @ExtendWith(MockitoExtension.class)
// @Slf4j
// public class UserServiceTest {

// @Mock
// private UserRepository userRepository;

// @Mock
// private PasswordEncoder passwordEncoder;

// @InjectMocks
// private UserServiceImpl userService;

// @Test
// public void 로그인() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// given(userRepository.findById(anyString()))
// .willReturn(Optional.of(User.builder().id(id).pw(encoder.encode(pw)).build()));
// given(passwordEncoder.matches(anyString(), anyString()))
// .willReturn(encoder.matches(pw, encoder.encode(pw)));

// // when
// userService.login(id, pw);

// // then
// }

// @Test
// public void 로그인_실패() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// given(userRepository.findById(anyString()))
// .willReturn(Optional.of(User.builder().id(id).pw(encoder.encode(pw)).build()));
// given(passwordEncoder.matches(anyString(), anyString()))
// .willReturn(encoder.matches("실패", encoder.encode(pw)));

// // when
// try {
// userService.login(id, pw);
// fail();
// } catch (MismatchPasswdException e) {
// log.info(e.getMessage());
// }

// // then
// }

// @Test
// public void 회원가입() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// given(userRepository.save(any())).willReturn(null);
// given(passwordEncoder.encode(pw)).willReturn(encoder.encode(pw));
// given(userRepository.existsById(id)).willReturn(false);

// // when
// userService.signUp(UserDto.builder().id(id).pw(pw).build());
// }

// @Test
// public void 회원가입_실패() {
// // given
// final String id = "userId";
// final String pw = "userPw";
// given(userRepository.existsById(id)).willReturn(true);

// // when
// try {
// userService.signUp(UserDto.builder().id(id).pw(pw).build());
// fail();
// } catch (DuplicateUserException e) {
// log.info(e.getMessage());
// }
// }

// @Test
// public void 유저정보가져오기() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// final String nick = "test";
// given(userRepository.findById(anyString()))
// .willReturn(Optional.of(User.builder().id(id).pw(encoder.encode(pw)).nickName(nick).build()));

// // when
// UserDto userDto = userService.getUserInfo(id);

// // then
// assertThat(userDto.getId()).isEqualTo(id);
// assertThat(userDto.getPw()).isNull();
// assertThat(userDto.getNickName()).isEqualTo(nick);
// }

// @Test
// public void 유저리스트가져오기() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final User user1 =
// User.builder().id("user1").pw(encoder.encode("test")).build();
// final User user2 =
// User.builder().id("user2").pw(encoder.encode("test")).build();
// given(userRepository.findAll())
// .willReturn(Arrays.asList(user1, user2));

// // when
// List<UserDto> users = userService.getAllUsers();

// // then
// users.forEach(user -> {
// assertThat(user.getId()).containsAnyOf(user1.getId(), user2.getId());
// assertThat(user.getPw()).isNull();
// });
// }

// @Test
// public void 유저_수정하기() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// final String modiPw = "modiPw";
// final String modiNick = "modiNick";
// final User savedUser = User.builder().id(id).pw(encoder.encode(pw)).build();
// final UserDto paramUser =
// UserDto.builder().id(id).pw(modiPw).nickName(modiNick).build();
// given(userRepository.findById(anyString()))
// .willReturn(Optional.of(savedUser));
// given(passwordEncoder.encode(anyString()))
// .willReturn(encoder.encode(paramUser.getPw()));
// given(userRepository.save(any()))
// .willReturn(null);

// // when
// userService.modifyUser(id, paramUser);

// // then
// assertThat(encoder.matches(modiPw, savedUser.getPw())).isEqualTo(true);
// assertThat(savedUser.getNickName()).isEqualTo(modiNick);
// }

// @Test
// public void 유저_수정하기_아이디존재에러() {
// // given
// final String id = "userId";
// final String modiPw = "modiPw";
// final UserDto paramUser = UserDto.builder().id(null).pw(modiPw).build();
// given(userRepository.findById(anyString()))
// .willReturn(Optional.ofNullable(null));

// // when
// try {
// userService.modifyUser(id, paramUser);
// fail();
// } catch (NotFoundUserException e) {
// log.info(e.getMessage());
// }

// // then
// }

// @Test
// public void 유저_탈퇴() {
// // given
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// final String id = "userId";
// final String pw = "userPw";
// final User savedUser = User.builder().id(id).pw(encoder.encode(pw)).build();
// given(userRepository.findById(anyString()))
// .willReturn(Optional.of(savedUser));
// given(userRepository.save(any()))
// .willReturn(null);

// // when
// userService.withdrawal(id);

// // then
// }

// @Test
// void 비밀번호생성() {
// CustomDelegatingPasswordEncoder encoder = new
// CustomDelegatingPasswordEncoder();
// System.out.println(encoder.encode("admin"));
// }
// }
