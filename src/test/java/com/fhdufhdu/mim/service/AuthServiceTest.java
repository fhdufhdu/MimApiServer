package com.fhdufhdu.mim.service;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fhdufhdu.mim.entity.Auth;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.RefreshTokenValidException;
import com.fhdufhdu.mim.jwt.JwtManager;
import com.fhdufhdu.mim.repository.AuthRepository;
import com.fhdufhdu.mim.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private AuthRepository authRepository;
    @Mock
    private MemberRepository memberRepository;

    @Test
    void 새로운엑세스토큰발급_기존액세스토큰과같지않을때() {
        final String memberId = "memberId";
        final String rt = JwtManager.issueRefreshToken(memberId);
        final String at = JwtManager.issueAccessToken(memberId, Role.MEMBER);
        final String storedAt = JwtManager.issueAccessToken(memberId, Role.BANNED_MEMBER);

        when(authRepository.findByMemberIdAndToken(memberId, rt))
                .thenReturn(Optional.ofNullable(
                        Auth.builder()
                                .member(Member.builder().id(memberId).build())
                                .token(rt)
                                .recentAccessToken(storedAt)
                                .build()));

        try {
            authService.requestNewToken(rt, at);
        } catch (RefreshTokenValidException e) {
            log.info("예외발생 - 정상");
            log.info(e.getMessage());
        }
    }

}