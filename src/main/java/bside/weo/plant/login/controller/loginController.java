package bside.weo.plant.login.controller;

import bside.weo.plant.login.service.KakaoAPI;
import bside.weo.plant.login.service.NaverAPI;
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
@RequestMapping(value = "/api/member/login")
public class loginController {

    @Autowired
    private KakaoAPI kakaoAPI;
    @Autowired
    private NaverAPI naverAPI;

    @GetMapping(value = "")
    public String test(String testString) {

        return "hello world : " + testString;
    }

    @GetMapping(value = "/kakao/login")
    public HashMap<String, Object> kakaoLogin(@RequestParam("accessToken") String accessToken, HttpSession session) {
        // 받아온 인가코드로 accesstoken 발급
        /*
        String accessToken = kakaoAPI.getKakaoAccessToken(code);
        System.out.println("accessToken = " + accessToken);
        */

        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(accessToken);
        System.out.println("login Controller : userInfo = " + userInfo);

        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
        }
        
        // userId + kakao로 조회되는 DB의 PK값만 넘겨주기

        return userInfo;
    }

    @GetMapping(value = "/kakao/logout")
    public String kakaoLogout(HttpSession session) {
        kakaoAPI.logout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        return "index";
    }

    @GetMapping(value = "/naver/login")
    public HashMap<String, Object> naverLogin(@RequestParam("accessToken") String accessToken, HttpSession session) {
        HashMap<String, Object> userInfo = naverAPI.getUserInfo(accessToken);
        System.out.println("naver login userInfo = " + userInfo);

        return userInfo;
    }
}
