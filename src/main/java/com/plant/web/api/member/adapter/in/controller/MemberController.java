package com.plant.web.api.member.adapter.in.controller;

import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.plant.web.config.utill.RandomNickname.Nickname;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member")
public class MemberController {

    private final MemberInPort memberInPort;

    /**
     * 소셜 유저 정보 조회
     * @param accessToken
     * @param snsType
     * @return
     */
    @GetMapping(value = "/profile")
    public ResponseEntity<?> getProfile(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType) {
        ResponseEntity responseEntity = memberInPort.getProfile(accessToken, snsType);
        return ResponseEntity.ok(responseEntity.getBody());
    }

    /**
     * snsId 중복체크
     */
    @GetMapping(value = "/check-snsid")
    public int fineMember(String snsId) {
        List<Member> members = memberInPort.validateDuplicateUser(snsId);
        return members.size();
    }

    /**
     * 회원가입
     */
    @PostMapping(value = "/join")
    public void joinMember(Member snsId) {
        // 임시 닉네임 생성
        String nickname = Nickname();
        System.out.println("controller nickname = " + nickname);

        // 회원가입
        // MEMBER_ID, SNS_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
        //memberInfo.put("nickname", nickname);
        //joinMember(member);
    }

}
