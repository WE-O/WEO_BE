package com.plant.web.api.scrap.adapter.in.controller;

import com.plant.web.api.place.dto.PlaceDto;
import com.plant.web.api.scrap.application.port.in.ScrapInPort;
import com.plant.web.api.scrap.domain.Scrap;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/scrap")
@Api(tags = {"스크랩 관련 API"})
public class ScrapController {

    private final ScrapInPort scrapInPort;

    /**
     * 회원별 스크랩 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/{memberId}")
    @Operation(summary = "회원별 스크랩 리스트 조회")
    public ResponseEntity<?> findReviewsByMemberId(@PathVariable(value = "memberId") String memberId) {
        List scraps = scrapInPort.findScrapsByMemberId(memberId);
        return ResponseEntity.ok(scraps);
    }

    /**
     * 스크랩 추가
     * @param memberId
     * @param contentsId
     * @return
     */
    @PostMapping(value = "/{memberId}")
    @Operation(summary = "스크랩 추가")
    public ResponseEntity<?> addScrap(@PathVariable(value = "memberId") String memberId, @RequestParam("contentsId") Long contentsId) {
        Scrap result = scrapInPort.addScrap(memberId, contentsId);
        return ResponseEntity.ok(result);
    }

    /**
     * 스크랩 삭제
     * @param scrapId
     * @return
     */
    @DeleteMapping
    @Operation(summary = "스크랩 삭제")
    public ResponseEntity<?> deleteScrap(@RequestParam("scrapId") Long scrapId) {
        Long result = scrapInPort.deleteScrap(scrapId);
        return ResponseEntity.ok(result);
    }
}
