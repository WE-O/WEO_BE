package com.plant.web.api.review.adapter.in.controller;

import com.plant.web.api.review.application.port.in.ReviewInPort;
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
@RequestMapping(value = "/api/v1/review")
public class ReviewController {

    private final ReviewInPort reviewInPort;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/review/{memberId}")
    @Operation(summary = "회원별 리뷰 리스트 조회")
    public ResponseEntity<?> findReviewsByMemberId(@PathVariable(value = "memberId") String memberId) {
        List reviews = reviewInPort.findReviewsByMemberId(memberId);
        return ResponseEntity.ok(reviews);
    }

}
