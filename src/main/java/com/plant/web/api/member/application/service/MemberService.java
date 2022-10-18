package com.plant.web.api.member.application.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import com.plant.web.config.utill.RandomNickname;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService implements MemberInPort {

    private final MemberPersistenceOutPort memberPersistenceOutPort;

    /**
     * 회원 가입
     */
    @Transactional
    public Member join(Member member) {

        Member findUsers = findBySnsId(member.getSnsId());    //중복 회원 검증

        if(findUsers == null) {
            log.info("최초 회원 가입");
            //회원가입 진행
            //USER_ID, SNS_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
            //snsId, snsType, email, profileImg

            String nickname = "";

            //닉네임 중복 체크
            do {
                //임시닉네임 생성
                log.info("임시 닉네임 생성");
                nickname = RandomNickname.Nickname();
            } while (nicknameDupCheck(nickname) > 0);

            member.setNickname(nickname);

            //현재 시간 세팅
            member.setJoinDate(LocalDateTime.now());

            //탈퇴여부 N 세팅
            member.setDelYn('N');

            //회원가입
            memberPersistenceOutPort.save(member);
        } else {
            log.info("기존 회원 정보 조회");
            Long id = findUsers.getId();
            String nickname = findUsers.getNickname();
            LocalDateTime joinDate = findUsers.getJoinDate();
            LocalDateTime delDate = findUsers.getDelDate();
            char delYn = findUsers.getDelYn();

            member.setId(id);
            member.setNickname(nickname);
            member.setJoinDate(joinDate);
            member.setDelDate(delDate);
            member.setDelYn(delYn);
        }

        return member;
    }

    /**
     * 기존 가입 회원 정보 확인
     */
    private Member findBySnsId(String snsId) {
        log.info("기존 가입 회원 정보 확인");
        return memberPersistenceOutPort.findBySnsId(snsId);
    }

    /**
     * 닉네임 중복 확인
     */
    private int nicknameDupCheck(String nickname) {
        return memberPersistenceOutPort.findByNickname(nickname).size();
    }

    /**
     * 프로필 조회
     */
    @Override
    public ResponseEntity getProfile(String accessToken, String snsType) {

        ResponseEntity<?> resultMap = null;

        if ("kakao".equals(snsType)) {

            // 카카오 프로필 가져오기
            resultMap = memberPersistenceOutPort.requestKakaoProfile(memberPersistenceOutPort.generateProfileRequest(accessToken));

            System.out.println("kakao memberInfo = " + resultMap);

        } else if ("naver".equals(snsType)) {
            // 네이버 프로필 가져오기
            resultMap = memberPersistenceOutPort.requestNaverProfile(memberPersistenceOutPort.generateProfileRequest(accessToken));

            System.out.println("naver memberInfo = " + resultMap);
        }
        return  resultMap;
    }


}
