package com.plant.web.api.member.adapter.in.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    private final MemberPersistenceOutPort memberPersistenceOutPort;

    /**
     * 소셜 유저 정보 조회
     * @param accessToken
     * @param snsType
     * @return
     */
    @GetMapping(value = "/profile")
    public ResponseEntity<Member> getProfile(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType) {
        ResponseEntity responseEntity = memberInPort.getProfile(accessToken, snsType);

        JsonElement element = JsonParser.parseString(responseEntity.getBody().toString());
        JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
        String snsId = element.getAsJsonObject().get("id").getAsString();
        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
        String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

        Member member = new Member();
        member.setSnsId(snsId);
        member.setSnsType(snsType);
        member.setEmail(email);
        member.setProfileImg(profileImg);

        System.out.println("member111 = " + member);
        // 회원가입 처리
        member = memberInPort.join(member);
        System.out.println("member222 = " + member);

        //return ResponseEntity.ok(responseEntity.getBody());
        return new ResponseEntity<>(member, HttpStatus.OK);
    }

}
