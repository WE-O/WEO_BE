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
    public HashMap<String, Object> getProfile(@RequestParam("accessToken") String accessToken, @RequestParam("socialType") String socialType, HttpSession session) {

        HashMap<String, Object> userInfo = null;

        if("kakao".equals(socialType)) {

            // 카카오 프로필 가져오기
            String response = loginAPI.requestKakaoProfile(loginAPI.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
            String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
            String profileImg = properties.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("kakao response = " + response);
            // 가입 이력이 없으면? -> 회원가입 처리

            userInfo = new HashMap<>();
            userInfo.put("socialType", socialType);
            userInfo.put("email", email);
            userInfo.put("profileImg", profileImg);

        } else if("naver".equals(socialType)) {
            // 네이버 프로필 가져오기
            String response = loginAPI.requestNaverProfile(loginAPI.generateProfileRequest(accessToken)).getBody();

            // JSON 파싱
            JsonElement element = JsonParser.parseString(response);
            JsonObject res = element.getAsJsonObject().get("response").getAsJsonObject();
            String email = res.getAsJsonObject().get("email").getAsString();
            String profileImg = res.getAsJsonObject().get("profile_image").getAsString();

            System.out.println("naver response = " + response);

            userInfo = new HashMap<>();
            userInfo.put("socialType", socialType);
            userInfo.put("email", email);
            userInfo.put("profileImg", profileImg);
        }

        // userId + kakao로 조회되는 DB의 PK값만 넘겨주기

        return userInfo;
    }

}
