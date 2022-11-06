package com.plant.web.api.review.adapter.in.controller;

import com.plant.web.api.review.application.port.in.ReviewInPort;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
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
@RequestMapping(value = "/api/v1/review")
@Api(tags = {"리뷰 관련 API"})
public class ReviewController {

    private final ReviewInPort reviewInPort;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/{memberId}")
    @Operation(summary = "회원별 리뷰 리스트 조회")
    public ResponseEntity<?> findReviewsByMemberId(@PathVariable(value = "memberId") String memberId) {
        List reviews = reviewInPort.findReviewsByMemberId(memberId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * 리뷰 등록
     * @param memberId
     * @param placeId
     * @param contents
     * @param keywords
     * @return
     */
    @PostMapping(value = "/{memberId}")
    @Operation(summary = "리뷰 등록")
    public ResponseEntity<?> addReview(@PathVariable(value = "memberId") String memberId, @RequestParam("placeId") String placeId, @RequestParam("contents") String contents, @RequestParam("keywords") List<Long> keywords) {
        Review result = reviewInPort.addReview(memberId, placeId, contents, keywords);
        return ResponseEntity.ok(result);
    }
}
