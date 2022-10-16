package com.plant.web.api.member.application.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberInPort {

    private final MemberPersistenceOutPort memberPersistenceOutPort;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        /*
        List<Member> findUsers = validateDuplicateUser(member);    //중복 회원 검증

        if(findUsers.isEmpty()) {
            //회원가입 진행
            memberRepository.save(member);
        } else {
            //기존 User정보 조회 -> 필요한지 재확인
            findOne(member.getId());
        }

        return member.getId();
        */
        memberPersistenceOutPort.save(member);

        return member.getId();
    }

    /**
     * snsId로 이미 가입되어있는 회원인지 확인
     */
    public List<Member> validateDuplicateUser(String snsId) {
        List<Member> findUsers = memberPersistenceOutPort.findBySnsId(snsId);
        /*
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        */
        return findUsers;
    }

    /**
     * 기존 가입 회원 정보 확인
     */
    public Member findOne(Long userId) {
        return memberPersistenceOutPort.findOne(userId);
    }

    /**
     * 임시 닉네임 발급
     */


    /**
     * 임시 닉네임 중복 확인
     */

    /**
     * 프로필 조회
     */
    @Override
    public ResponseEntity getProfile(String accessToken, String snsType) {
        HashMap<String, Object> memberInfo = null;
        Member member = null;

        if ("kakao".equals(snsType)) {

            // 카카오 프로필 가져오기
            String response = memberPersistenceOutPort.requestKakaoProfile(memberPersistenceOutPort.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String snsId = element.getAsJsonObject().get("id").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            member = new Member();
            member.setSnsId(snsId);
            member.setSnsType(snsType);
            member.setEmail(email);
            member.setProfileImg(profileImg);

            System.out.println("kakao memberInfo = " + memberInfo);

        } else if ("naver".equals(snsType)) {
            // 네이버 프로필 가져오기
            String response = memberPersistenceOutPort.requestNaverProfile(memberPersistenceOutPort.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject res = element.getAsJsonObject().get("response").getAsJsonObject();
            String snsId = res.getAsJsonObject().get("id").getAsString();
            String email = res.getAsJsonObject().get("email").getAsString();
            String profileImg = res.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("naver response = " + response);

            /*memberInfo = new HashMap<>();
            memberInfo.put("snsId", snsId);
            memberInfo.put("snsType", snsType);
            memberInfo.put("email", email);
            memberInfo.put("profileImg", profileImg);*/

            member = new Member();
            member.setSnsId(snsId);
            member.setSnsType(snsType);
            member.setEmail(email);
            member.setProfileImg(profileImg);

            System.out.println("naver memberInfo = " + memberInfo);
        }
        return  null;
    }


}
