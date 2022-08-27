package com.fhdufhdu.mim.service;

import com.fhdufhdu.mim.dto.DateParam;
import com.fhdufhdu.mim.dto.member.Login;
import com.fhdufhdu.mim.dto.member.MemberInfo;
import com.fhdufhdu.mim.dto.member.SignUp;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundMemberException;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.service.util.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * POST /users/login
     * [service 테스트 항목]
     * 1. 멤버를 못찾을때 예외가 발생하는지
     * 2. 비밀번호가 맞지 않을때 예외가 발생하는지
     */
    MemberInfo login(Login login) {
        Member member = memberRepository.findById(login.getId()).orElseThrow(NotFoundMemberException::new);
        if (!passwordEncoder.matches(login.getPw(), member.getPw())) {
            throw new MismatchPasswdException();
        }
        return ServiceUtil.convertToDest(member, MemberInfo.class);
    }

    /**
     * POST /users/sign-up
     */
    MemberInfo signUp(SignUp singUp) {
        Member member = Member.builder()
                .pw(passwordEncoder.encode(singUp.getPw()))
                .id(singUp.getId())
                .nickname(singUp.getNickname())
                .build();
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * GET /users/id/{id}
     */
    boolean existId(String id) {
        return !memberRepository.findById(id).isEmpty();
    }

    /**
     * GET /users/nick-name/{nickName}
     */
    boolean existNickName(String nickName) {
        return !memberRepository.findByNickname(nickName).isEmpty();
    }

    /**
     * GET /users/{id}
     */
    MemberInfo getUserInfo(String id) {
        return ServiceUtil.convertToDest(memberRepository.findById(id).orElseThrow(NotFoundMemberException::new), MemberInfo.class);
    }

    /**
     * PUT /users/{id}/nickname
     */
    MemberInfo changeNickname(String id, String nickName) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        member.setNickname(nickName);
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * PUT /users/{id}/password
     */
    MemberInfo changePassword(String id, String oldPw, String newPw) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        if (!passwordEncoder.matches(oldPw, member.getPw())) throw new MismatchPasswdException();
        member.setPw(passwordEncoder.encode(newPw));
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * PUT /users/{id}/ban
     * [service 테스트 항목]
     * 1. 사용자 재제 일자가 잘 적용되는지
     */
    MemberInfo banUser(String id, DateParam dateParam) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        member.setBanEndDate(Timestamp.valueOf(LocalDateTime.of(dateParam.getYear(), dateParam.getMonth(), dateParam.getDay(), dateParam.getHours(), 0)));
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * DELETE /users/{id}
     * 1. 사용자 제거 중 예외 발생 시 처리가 잘 되는지
     */
    boolean withdrawal(String id) {
        try {
            memberRepository.deleteById(id);
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }
}