package com.plant.web.api.member.adapter.in.controller;

import com.plant.web.api.member.application.port.in.MemberInPort;
import com.plant.web.api.member.domain.Member;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 로그인 컨트롤러
* */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member")
@Api(tags = {"회원 관련 API"})
public class MemberController {

    private final MemberInPort memberInPort;

    /**
     * 소셜 유저 정보 조회
     * @param accessToken
     * @param snsType
     * @return
     */
    @GetMapping(value = "/join")
    @Operation(summary = "로그인", description = "토큰값으로 sns정보를 조회 후, 받아온 정보가 DB에 없으면 회원가입 후 로그인")
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "accessToken"
            , value = "accessToken"
            , required = true
            , dataType = "string"
        )
        , @ApiImplicitParam(
            name = "snsType"
            , value = "소셜 타입 (kakao / naver)"
            , required = true
            , dataType = "string"
        )
    })
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
    @Operation(summary = "회원 탈퇴")
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
    @Operation(summary = "회원 정보 조회")
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
    @Operation(summary = "닉네임 수정")
    @ApiImplicitParam(
            name = "nickname"
            , value = "수정할 닉네임"
            , required = true
            , dataType = "string"
    )
    public ResponseEntity<?> modifyNickname(@RequestParam("memberId") String memberId, @RequestParam("nickname") String nickname) {
        Long count = memberInPort.modifyNickname(memberId, nickname);
        return ResponseEntity.ok(count);
    }


    /**
     * 회원별 북마크 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/bookmark/{memberId}")
    @Operation(summary = "회원별 북마크 리스트 조회")
    public ResponseEntity<?> findBookmarksByMemberId(@PathVariable(value = "memberId") String memberId) {
        List bookmarks = memberInPort.findBookmarksByMemberId(memberId);
        return ResponseEntity.ok(bookmarks);
    }

    /**
     * 북마크 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    @PutMapping(value = "/bookmark/{memberId}")
    @Operation(summary = "회원별 북마크 수정")
    public ResponseEntity<?> modifyBookmark(
            @PathVariable(value = "memberId") String memberId
            , @RequestParam("bookmarkId") String bookmarkId
            , @RequestParam("memo") String memo) {

        Long modifyBookmark = memberInPort.modifyBookmark(memberId, bookmarkId, memo);
        return ResponseEntity.ok(modifyBookmark);
    }

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/review/{memberId}")
    @Operation(summary = "회원별 리뷰 리스트 조회")
    public ResponseEntity<?> findReviewsByMemberId(@PathVariable(value = "memberId") String memberId) {
        List reviews = memberInPort.findReviewsByMemberId(memberId);
        return ResponseEntity.ok(reviews);
    }
}
