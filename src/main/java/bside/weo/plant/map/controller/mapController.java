package bside.weo.plant.map.controller;

import bside.weo.plant.login.service.loginAPI;
import bside.weo.plant.map.service.kakaoMapAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping(value = "/api/v1/map")
public class mapController {

    @Autowired
    private kakaoMapAPI kakaoMapAPI;

    @GetMapping(value = "/place")
    public ResponseEntity<String> getPlace(
            @RequestParam("restApiKey") String restApiKey
            , @RequestParam("query") String query
            , @RequestParam("x") String x
            , @RequestParam("y") String y) {

        ResponseEntity<String> placeInfo = null;
        placeInfo = kakaoMapAPI.requestPlaceInfo(restApiKey, query, x, y);

        return placeInfo;
    }

}
