package com.fhdufhdu.mim.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fhdufhdu.mim.dto.DateParam;
import com.fhdufhdu.mim.dto.member.ChangeMemberInfo;
import com.fhdufhdu.mim.dto.member.ChangePassword;
import com.fhdufhdu.mim.dto.member.Login;
import com.fhdufhdu.mim.dto.member.MemberInfo;
import com.fhdufhdu.mim.dto.member.SignUp;
import com.fhdufhdu.mim.entity.Member;
import com.fhdufhdu.mim.entity.Role;
import com.fhdufhdu.mim.exception.MismatchPasswdException;
import com.fhdufhdu.mim.exception.NotFoundMemberException;
import com.fhdufhdu.mim.repository.MemberRepository;
import com.fhdufhdu.mim.utils.ServiceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * GET /login?id={id}&pw={pw}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>멤버를 못찾을때 예외가 발생하는지
     * <li>비밀번호가 맞지 않을때 예외가 발생하는지
     * </ol>
     */
    public MemberInfo login(Login login) {
        Member member = memberRepository.findById(login.getId()).orElseThrow(NotFoundMemberException::new);
        if (!passwordEncoder.matches(login.getPw(), member.getPw())) {
            throw new MismatchPasswdException();
        }
        return ServiceUtil.convertToDest(member, MemberInfo.class);
    }

    /**
     * POST /users/sign-up
     */
    public MemberInfo signUp(SignUp signUp) {
        Member member = Member.builder()
                .pw(passwordEncoder.encode(signUp.getPw()))
                .id(signUp.getId())
                .nickname(signUp.getNickname())
                .role(Role.MEMBER)
                .build();
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * GET /users/id/{id}
     */
    public boolean existId(String id) {
        return !memberRepository.findById(id).isEmpty();
    }

    /**
     * GET /users/nick-name/{nickName}
     */
    public boolean existNickname(String nickname) {
        return !memberRepository.findByNickname(nickname).isEmpty();
    }

    /**
     * GET /users/{id}
     */
    public MemberInfo getUserInfo(String id) {
        return ServiceUtil.convertToDest(memberRepository.findById(id).orElseThrow(NotFoundMemberException::new),
                MemberInfo.class);
    }

    /**
     * PUT /users/{id}/nickname
     * <p>
     * [service 테스트 항목]
     * </p>
     * <ol>
     * <li>사용자 재제 일자가 잘 적용되는지
     * <li>날짜 범위 밖일때 예외 처리
     * </ol>
     */
    public MemberInfo changeMemberInfo(String id, ChangeMemberInfo info, String auth) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        ServiceUtil.checkAdminMember(member.getId());

        member.setNickname(info.getNickname());
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * PUT /users/{id}/password
     */
    public MemberInfo changePassword(String id, ChangePassword cp) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        ServiceUtil.checkAdminMember(member.getId());

        if (!passwordEncoder.matches(cp.getOldPw(), member.getPw()))
            throw new MismatchPasswdException();
        member.setPw(passwordEncoder.encode(cp.getNewPw()));
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * PUT /users/{id}/ban
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>사용자 재제 일자가 잘 적용되는지
     * <li>날짜 범위 밖일때 예외 처리
     * </ol>
     */
    public MemberInfo banUser(String id, DateParam dateParam) {
        Member member = memberRepository.findById(id).orElseThrow(NotFoundMemberException::new);
        member.setBanEndDate(Timestamp.valueOf(LocalDateTime.of(dateParam.getYear(), dateParam.getMonth(),
                dateParam.getDay(), dateParam.getHours(), 0)));
        return ServiceUtil.convertToDest(memberRepository.save(member), MemberInfo.class);
    }

    /**
     * DELETE /users/{id}
     * <p>
     * [service 테스트 항목]
     * <ol>
     * <li>사용자 제거 중 예외 발생 시 처리가 잘 되는지
     * </ol>
     */
    public boolean withdrawal(String id, String auth) {
        try {
            ServiceUtil.checkAdminMember(id);
            memberRepository.deleteById(id);
        } catch (Exception e) {
            Arrays.stream(e.getStackTrace()).forEach(st -> log.error(st.toString()));
            return false;
        }
        return true;
    }

}