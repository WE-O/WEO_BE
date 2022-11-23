package com.plant.web.api.placeKeyword.adapter.in.controller;

import com.plant.web.api.placeKeyword.application.port.in.PlaceKeywordInPort;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/keyword")
@Api(tags = {"키워드 API"})
public class PlaceKeywordController {

    private final PlaceKeywordInPort placeKeywordInPort;

    /**
     * 식물집사가 선택한 키워드
     * @param placeId
     * @return
     */
    @GetMapping(value = "/{placeId}")
    @Operation(summary = "장소별 식물집사가 선택한 키워드")
    public ResponseEntity<?> getKeywordList(@PathVariable(value = "placeId") String placeId) {
        List result = placeKeywordInPort.getKeywordList(placeId);
        return ResponseEntity.ok(result);
    }

}
