package bside.weo.plant.kakaoLogin.controller;

import bside.weo.plant.kakaoLogin.service.KakaoAPI;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping(value = "/api/member/kakao")
public class HomeController {

    @Autowired
    private KakaoAPI kakaoAPI;

    @GetMapping(value = "")
    public String test(String testString) {
        return "hello world : " + testString;
    }

    @GetMapping(value = "/login")
    public String login(@RequestParam("accessToken") String accessToken, HttpSession session) {
        HashMap<String, Object> userInfo = kakaoAPI.getUserInfo(accessToken);
        System.out.println("login Controller : userInfo = " + userInfo);

        // 클라이언트의 이메일이 존재할 때 세션에 해당 이메일과 토큰 등록
        if (userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("accessToken", accessToken);
        }
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        kakaoAPI.logout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        return "index";
    }
}
