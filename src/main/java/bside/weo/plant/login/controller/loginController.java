package bside.weo.plant.login.controller;

import bside.weo.plant.login.service.KakaoAPI;
import bside.weo.plant.login.service.loginAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/user")
public class loginController {
    @Autowired
    private loginAPI loginAPI;

    @GetMapping(value = "")
    public String test(String testString) {

        return "hello world : " + testString;
    }

    // 소셜 유저 정보 조회
    @GetMapping(value = "/profile")
    public HashMap<String, Object> getProfile(@RequestParam("accessToken") String accessToken, @RequestParam("snsType") String snsType, HttpSession session) {

        HashMap<String, Object> userInfo = null;

        if("kakao".equals(snsType)) {

            // 카카오 프로필 가져오기
            String response = loginAPI.requestKakaoProfile(loginAPI.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String snsId = element.getAsJsonObject().get("id").getAsString();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("kakao response = " + response);

            userInfo = new HashMap<>();
            userInfo.put("snsId", snsId);
            userInfo.put("snsType", snsType);
            userInfo.put("email", email);
            userInfo.put("profileImg", profileImg);

            System.out.println("kakao userInfo = " + userInfo);

        } else if("naver".equals(snsType)) {
            // 네이버 프로필 가져오기
            String response = loginAPI.requestNaverProfile(loginAPI.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject res = element.getAsJsonObject().get("response").getAsJsonObject();
            String snsId = res.getAsJsonObject().get("id").getAsString();
            String email = res.getAsJsonObject().get("email").getAsString();
            String profileImg = res.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("naver response = " + response);

            userInfo = new HashMap<>();
            userInfo.put("snsId", snsId);
            userInfo.put("snsType", snsType);
            userInfo.put("email", email);
            userInfo.put("profileImg", profileImg);

            System.out.println("naver userInfo = " + userInfo);
        }

        // 가입 이력이 없으면? -> 회원가입 처리

        // 임시 닉네임 생성
        String nickname = loginAPI.getUsername();

        // 회원가입
        // USER_ID, SNS_ID, NICKNAME, EMAIL, PROFILE_IMG, SNS_TYPE, JOIN_DATE
        userInfo.put("nickname", nickname);

        return userInfo;
    }

}
