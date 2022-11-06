package com.plant.web.api.scrap.adapter.in.controller;

import com.plant.web.api.scrap.application.port.in.ScrapInPort;
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
@RequestMapping(value = "/api/v1/scrap")
@Api(tags = {"스크랩 관련 API"})
public class ScrapController {

    private final ScrapInPort scrapInPort;

    @GetMapping(value = "/{memberId}")
    @Operation(summary = "회원별 스크랩 리스트 조회")
    public ResponseEntity<?> findReviewsByMemberId(@PathVariable(value = "memberId") String memberId) {
        List scraps = scrapInPort.findScrapsByMemberId(memberId);
        return ResponseEntity.ok(scraps);
    }
}
