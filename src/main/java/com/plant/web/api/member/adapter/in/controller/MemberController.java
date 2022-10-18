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

import javax.servlet.http.HttpSession;

/**
 * 로그인 컨트롤러
* */
@Slf4j
@RestController
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
    @GetMapping(value = "/join")
    public ResponseEntity<?> getJoin(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType) {
        Member responseEntity = memberInPort.join(accessToken, snsType);
        return ResponseEntity.ok(responseEntity);
    }

    /**
     * 회원 탈퇴
     * @param snsid
     * @return
     */
    @DeleteMapping("/{snsid}")
    public ResponseEntity<?> accountRemove(@PathVariable(value = "snsid") String snsid, HttpSession httpSession){
        Long count = memberInPort.accountRemove(snsid);
        return ResponseEntity.ok(count);
    }

    /**
     * 프로필 조회
     * @param snsid
     * @return
     */
    @GetMapping("/{snsid}")
    public ResponseEntity<?> getProfile(@PathVariable(value = "snsid") String snsid, HttpSession httpSession){
        Member member = memberInPort.findBySnsId(snsid);
        return ResponseEntity.ok(member);
    }



}
