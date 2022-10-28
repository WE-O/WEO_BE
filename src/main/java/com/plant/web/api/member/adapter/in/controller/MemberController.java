package com.plant.web.api.member.adapter.in.controller;

import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Operation(summary = "로그인", description = "토큰값으로 sns정보를 조회 후, 받아온 정보가 DB에 없으면 회원가입 후 로그인")
    @GetMapping(value = "/join")
    public ResponseEntity<?> getJoin(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType) {
        Member responseEntity = memberInPort.join(accessToken, snsType);
        return ResponseEntity.ok(responseEntity);
    }

    /**
     * 회원 탈퇴
     * @param memberId
     * @return
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<?> accountRemove(@PathVariable(value = "memberId") String memberId, HttpSession httpSession){
        Long count = memberInPort.accountRemove(memberId);
        return ResponseEntity.ok(count);
    }

    /**
     * 프로필 조회
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<?> getProfile(@PathVariable(value = "memberId") String memberId, HttpSession httpSession){
        Member member = memberInPort.findByMemberId(memberId);
        return ResponseEntity.ok(member);
    }

    /**
     * 닉네임 수정
     * @param memberId
     * @return
     */
    @PutMapping(value = "/nickname")
    public ResponseEntity<?> modifyNickname(@RequestParam("memberId") String memberId, @RequestParam("nickname") String nickname) {
        Long count = memberInPort.modifyNickname(memberId, nickname);
        return ResponseEntity.ok(count);
    }


}
