package bside.weo.plant.service;

import bside.weo.plant.domain.Member;
import bside.weo.plant.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 네이버 프로필 조회
     * @param request
     * @return
     */
    public ResponseEntity<String> requestNaverProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange("https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class);
    }

    /**
     * 카카오 프로필 조회
     * @param request
     * @return
     */
    public ResponseEntity<String> requestKakaoProfile(HttpEntity request) {
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.exchange("https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                request,
                String.class);
    }

    /**
     * 소셜 로그인 api 준비
     * @param accessToken
     * @return
     */
    public HttpEntity<MultiValueMap<String, String>> generateProfileRequest(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return new HttpEntity<>(headers);
    }

    /**
     * 임시 닉네임 생성
     * @return
     */
    public String getNickname() {
        String result = "";
        String[] firstDepth = {"행복한", "유쾌한", "밝은", "따뜻한", "다정한"
                , "명랑한", "용감한", "힐링하는", "너그러운", "사교적인"
                , "슬기로운", "현명한", "성실한", "외향적인", "귀여운"
                , "긍적적인", "매력적인", "솔직한", "우아한", "싱그러운"};
        
        String[] secondDepth = {"스킨답서스", "스파티필름", "몬스테라", "산세베리아", "레티지아"
                , "제라니움", "콩고", "파키라", "피토니아", "싱고니움"
                , "틸란드시아", "아이비", "포인세티아", "베고니아", "칼라디움"
                , "안스리움", "커피나무", "시서스", "레몬라임", "행운목"};


        double firstRandom = Math.floor(Math.random() * (firstDepth.length - 1));
        int firstNum = (int)firstRandom;

        double secondRandom = Math.floor(Math.random() * (secondDepth.length - 1));
        int secondNum = (int)secondRandom;

        result = firstDepth[firstNum] + secondDepth[secondNum];
        return result;
    }

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
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * snsId로 이미 가입되어있는 회원인지 확인
     */
    public List<Member> validateDuplicateUser(String snsId) {
        List<Member> findUsers = memberRepository.findBySnsId(snsId);
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
        return memberRepository.findOne(userId);
    }

    /**
     * 임시 닉네임 발급
     */


    /**
     * 임시 닉네임 중복 확인
     */

}
