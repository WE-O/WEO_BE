package com.plant.web.api.place.adapter.in.controller;

import com.plant.web.api.place.application.port.in.PlaceInPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@Api(tags = {"지도 검색 조회 API"})
public class PlaceController {
    private final PlaceInPort placeInPort;

    public PlaceController(PlaceInPort placeInPort) {
        this.placeInPort = placeInPort;
    }

    @GetMapping("/places")
    @ApiParam(name = "keyword",value = "keyword")
    @Operation(summary = "지도 검색", description = "메인페이지에서 지도 검색")
    public ResponseEntity<?> get(@RequestParam(name = "keyword") String keyword){
        JSONObject response = placeInPort.getPlaces(keyword);
        return ResponseEntity.ok(response);
    }
}
