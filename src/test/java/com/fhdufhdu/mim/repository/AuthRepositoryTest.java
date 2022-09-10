package com.fhdufhdu.mim.repository;

import com.fhdufhdu.mim.entity.Auth;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.RefreshTokenValidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AuthRepositoryTest {
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private AuthRepository authRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member getMember(int id) {
        return memberRepository.save(Member.builder()
                .pw(encoder.encode("password"))
                .role(Role.MEMBER)
                .nickname("nickname")
                .id("userid" + 1)
                .build());
    }

    private List<Auth> getAuth() {
        List<Auth> authList = new ArrayList<>();
        Member member1 = getMember(1);
        Member member2 = getMember(2);
        IntStream.range(0, 10).forEach(idx -> authList.add(Auth.builder()
                .id((long) idx)
                .member(idx % 2 == 0 ? member2 : member1)
                .token(String.valueOf(idx))
                .build()));
        return authRepository.saveAll(authList);
    }

    @Test
    void 리프레시토큰가져오기() {
        final String userId = "userid1";
        final String token = "1";
        getAuth();
        Auth auth = authRepository.findByMemberIdAndToken(userId, token).orElseThrow(RefreshTokenValidException::new);
        assertThat(auth.getMember().getId()).hasToString(userId);
        assertThat(auth.getToken()).hasToString(token);
    }
}
