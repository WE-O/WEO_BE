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
     * @param snsId
     * @return
     */
    @DeleteMapping("/{snsId}")
    public ResponseEntity<?> accountRemove(@PathVariable(value = "snsId") String snsId, HttpSession httpSession){
        Long count = memberInPort.accountRemove(snsId);
        return ResponseEntity.ok(count);
    }

    /**
     * 프로필 조회
     * @param snsId
     * @return
     */
    @GetMapping("/{snsId}")
    public ResponseEntity<?> getProfile(@PathVariable(value = "snsId") String snsId, HttpSession httpSession){
        Member member = memberInPort.findBySnsId(snsId);
        return ResponseEntity.ok(member);
    }

    /**
     * 닉네임 수정
     * @param snsId
     * @return
     */
    @PutMapping(value = "/nickname")
    public ResponseEntity<?> modifyNickname(@RequestParam("snsId") String snsId, @RequestParam("nickname") String nickname) {
        Long count = memberInPort.modifyNickname(snsId, nickname);
        return ResponseEntity.ok(count);
    }


}
