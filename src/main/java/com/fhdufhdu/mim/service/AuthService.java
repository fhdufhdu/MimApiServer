package com.fhdufhdu.mim.service;

import java.util.EnumMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.fhdufhdu.mim.entity.Auth;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.exception.RefreshTokenValidException;
import com.fhdufhdu.mim.repository.AuthRepository;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.security.JwtManager;
import com.fhdufhdu.mim.security.JwtType;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthRepository authRepository;
    private final MemberRepository memberRepository;

    /**
     * GET /refresh
     * <p>
     * 리프레시 토큰으로 새로운 엑세스 토큰 발급함
     * </p>
     * 현재 가지고 있는 엑세스토큰이 최근 발급된 엑세스 토큰과 다르다면 토큰이 노출된 것으로 판단하여,
     * 해당 리프레시 토큰을 제거하도록 함
     * </p>
     * <p>
     * [repository 테스트 항목]
     * </p>
     * <ol>
     * <li>멤버아이디와, 토큰에 맞게 결과가 나오는 지</li>
     * </ol>
     * <p>
     * [service 테스트 항목]
     * </p>
     * <ol>
     * <li>외부에서 받은 userAgent와 token에서 추출한 userAgent가 다를때 예외발생하는지</li>
     * <li>DB에서 리프레시 토큰을 못찾으면 예외가 발생하는지</li>
     * <li>멤버를 못찾았을 때 예외가 발생하는지</li>
     * <li>토큰에 정확한 정보가 담겨서 발급되는지</li>
     * </ol>
     */
    public Map<JwtType, String> requestNewToken(String refreshToken, String accessToken) {
        String memberId = JwtManager.getMemberId(refreshToken);

        Auth auth = authRepository.findByMemberIdAndToken(memberId, refreshToken)
                .orElseThrow(RefreshTokenValidException::new);
        if (!auth.getRecentAccessToken().equals(accessToken) || JwtManager.isExpired(refreshToken)) {
            authRepository.delete(auth);
            throw new RefreshTokenValidException();
        }
        Member member = memberRepository.findById(memberId).orElseThrow(RefreshTokenValidException::new);
        String newAccessToken = JwtManager.issueAccessToken(memberId, member.getRole());
        String newRefreshToken = JwtManager.issueRefreshToken(memberId);
        auth.setRecentAccessToken(newAccessToken);
        auth.setToken(newRefreshToken);
        authRepository.save(auth);
        Map<JwtType, String> result = new EnumMap<>(JwtType.class);
        result.put(JwtType.ACCESS, newAccessToken);
        result.put(JwtType.REFRESH, newRefreshToken);
        return result;
    }

    public void saveTokens(String memberId, String refreshToken, String accessToken) {
        Member member = memberRepository.findById(memberId).orElseThrow(RefreshTokenValidException::new);
        Auth auth = Auth.builder()
                .member(member)
                .token(refreshToken)
                .recentAccessToken(accessToken)
                .build();
        authRepository.save(auth);
    }
}
