package bside.weo.plant.login.controller;

import bside.weo.plant.login.service.KakaoAPI;
import bside.weo.plant.login.service.loginAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private KakaoAPI kakaoAPI;
    @Autowired
    private loginAPI loginAPI;

    @GetMapping(value = "")
    public String test(String testString) {

        return "hello world : " + testString;
    }

    // 소셜 유저 정보 조회
    @GetMapping(value = "/profile")
    public HashMap<String, Object> kakaoLogin(@RequestParam("accessToken") String accessToken, @RequestParam("socialType") String socialType, HttpSession session) {
        // 받아온 인가코드로 accesstoken 발급
        /*
        String accessToken = kakaoAPI.getKakaoAccessToken(code);
        System.out.println("accessToken = " + accessToken);
        */

        HashMap<String, Object> userInfo = null;

        System.out.println("socialType = " + socialType);
        if("kakao".equals(socialType)) {
            // 카카오 프로필 가져오기
            userInfo = kakaoAPI.getUserInfo(accessToken);
            System.out.println("login Controller : userInfo = " + userInfo);

            // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
            if (userInfo.get("email") != null) {
                session.setAttribute("userId", userInfo.get("email"));
                session.setAttribute("accessToken", accessToken);
            }
        } else if("naver".equals(socialType)) {
            // 네이버 프로필 가져오기
        }

        // userId + kakao로 조회되는 DB의 PK값만 넘겨주기

        return userInfo;
    }

    // test ------
    @GetMapping(value = "/naver")
    public String naverLogin(@RequestParam("accessToken") String accessToken, HttpSession session) {
        System.out.println("확인 = " + loginAPI.requestNaverProfile(loginAPI.generateProfileRequest(accessToken)));
        return loginAPI.requestNaverProfile(loginAPI.generateProfileRequest(accessToken)).getBody();
    }

    @GetMapping(value = "/kakao")
    public String kakaoLogin2(@RequestParam("accessToken") String accessToken, HttpSession session) {

        return loginAPI.requestKakaoProfile(loginAPI.generateProfileRequest(accessToken)).getBody();
    }

}
