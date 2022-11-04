package com.plant.web.api.member.application.port.out;

import com.plant.web.api.member.domain.Member;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.List;

public interface MemberPersistenceOutPort {

    /**
     * 프로필 요청
     */
    HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken);

    /**
     * 카카오 프로필
     */
    ResponseEntity<String> requestKakaoProfile(HttpEntity generateProfileRequest);

    /**
     * 네이버 프로필
     */
    ResponseEntity<String> requestNaverProfile(HttpEntity generateProfileRequest);

    /**
     * 회원 가입
     */
    void save(Member member);

    /**
     * memberId 가입 회원 확인
     */
    Member findByMemberId(String memberId);

    /**
     * 닉네임 중복 체크
     */
    List<Member> findByNickname(String nickname);

    /**
     * 회원 탈퇴
     * @param memberId
     * @return
     */
    Long accountRemove(String memberId);

    /**
     * 닉네임 수정
     * @param memberId
     * @param nickname
     * @return
     */
    Long modifyNickname(String memberId, String nickname);
}
