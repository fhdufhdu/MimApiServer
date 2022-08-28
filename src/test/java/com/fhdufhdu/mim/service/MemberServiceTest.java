package com.fhdufhdu.mim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.DateTimeException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fhdufhdu.mim.dto.DateParam;
import com.fhdufhdu.mim.dto.member.Login;
import com.fhdufhdu.mim.dto.member.MemberInfo;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundMemberException;
import com.fhdufhdu.mim.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class MemberServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    void 로그인_멤버못찾음() {
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(null));
        try {
            memberService.login(Login.builder().build());
            fail();
        } catch (NotFoundMemberException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

    @Test
    void 로그인_비밀번호틀릴때() {
        final String id = "user";
        final String pw = "passwd";
        final String pw1 = "passwd1";
        Login login = Login.builder().id(id).pw(pw).build();
        when(memberRepository.findById(any()))
                .then(ivc -> Optional
                        .ofNullable(Member.builder().id(ivc.getArgument(0)).pw(passwordEncoder.encode(pw1)).build()));
        try {
            memberService.login(login);
            fail();
        } catch (MismatchPasswdException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

    @Test
    void 사용자제재_제제일자적용여부() {
        final String id = "user";
        final DateParam dateParam = DateParam.builder()
                .year(2022)
                .month(9)
                .day(10)
                .hours(14)
                .build();
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder()
                .id(id)
                .pw("pw")
                .nickname("nick")
                .build()));
        when(memberRepository.save(any())).then(ivc -> ivc.getArgument(0));
        MemberInfo result = memberService.banUser(id, dateParam);
        assertThat(result.getBanEndDate()).isEqualTo(dateParam.toTimestamp());
    }

    @Test
    void 사용자제재_날짜범위밖일때() {
        final String id = "user";
        final DateParam dateParam = DateParam.builder()
                .year(2022)
                .month(11)
                .day(31)
                .build();
        when(memberRepository.findById(any())).thenReturn(Optional.ofNullable(Member.builder().build()));
        try {
            memberService.banUser(id, dateParam);
            fail();
        } catch (DateTimeException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }
}
