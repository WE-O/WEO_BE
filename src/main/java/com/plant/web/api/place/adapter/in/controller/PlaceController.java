package com.plant.web.api.place.adapter.in.controller;

import com.plant.web.api.place.application.port.in.PlaceInPort;
import com.plant.web.api.place.domain.Place;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/places")
@Api(tags = {"지도 검색 조회 API"})
public class PlaceController {
    private final PlaceInPort placeInPort;

    public PlaceController(PlaceInPort placeInPort) {
        this.placeInPort = placeInPort;
    }

    @GetMapping
    @ApiParam(name = "검색단어",value = "검색단어")
    @Operation(summary = "지도 검색", description = "메인페이지에서 지도 검색")
    public ResponseEntity<?> Places(@RequestParam(name = "keyword") String keyword){
        JSONObject response = placeInPort.getPlaces(keyword);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ApiParam(name = "상품Id",value = "상품Id")
    @Operation(summary = "상세 조회", description = "해당 정보 상세 조회")
    public ResponseEntity<?> PlaceDetails(@PathVariable(name = "id") String id){
        Place response = placeInPort.getPlaceDetails(id);
        return ResponseEntity.ok(response);
    }
}
