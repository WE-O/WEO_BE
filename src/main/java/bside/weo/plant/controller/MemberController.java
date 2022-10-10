package bside.weo.plant.controller;

import bside.weo.plant.domain.Member;
import bside.weo.plant.service.MemberService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/member")
public class MemberController {

    //@Autowired
    private final MemberService memberService;

    @GetMapping(value = "")
    public String test(String testString) {

        return "hello world : " + testString;
    }

    /**
     * 소셜 유저 정보 조회
     * @param accessToken
     * @param snsType
     * @return
     */
    @GetMapping(value = "/profile")
    public Member getProfile(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType) {
        log.info("LoginController /api/v1/member/profile");
        HashMap<String, Object> memberInfo = null;
        Member member = null;

        if("kakao".equals(snsType)) {

            // 카카오 프로필 가져오기
            String response = memberService.requestKakaoProfile(memberService.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String snsId = element.getAsJsonObject().get("id").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("kakao response = " + response);

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

            System.out.println("kakao memberInfo = " + memberInfo);

        } else if("naver".equals(snsType)) {
            // 네이버 프로필 가져오기
            String response = memberService.requestNaverProfile(memberService.generateProfileRequest(accessToken)).getBody();

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

        return member;
    }

    /**
     * snsId 중복체크
     */
    @GetMapping(value = "/check-snsid")
    public int fineMember(String snsId) {
        List<Member> members = memberService.validateDuplicateUser(snsId);
        return members.size();
    }

    /**
     * 회원가입
     */
    @PostMapping(value = "/join")
    public void joinMember(Member snsId) {
        // 임시 닉네임 생성
        String nickname = memberService.getNickname();

        // 회원가입
        // MEMBER_ID, SNS_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
        //memberInfo.put("nickname", nickname);
        //joinMember(member);


    }

}
