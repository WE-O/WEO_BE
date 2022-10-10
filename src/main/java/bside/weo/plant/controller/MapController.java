package bside.weo.plant.controller;

import bside.weo.plant.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/map")
public class MapController {

    private final MapService mapService;

    @GetMapping(value = "/place")
    public ResponseEntity<String> getPlace(
            @RequestParam("restApiKey") String restApiKey
            , @RequestParam("query") String query
            , @RequestParam("x") String x
            , @RequestParam("y") String y) {

        ResponseEntity<String> placeInfo = null;
        placeInfo = mapService.requestPlaceInfo(restApiKey, query, x, y);

        return placeInfo;
    }

}
