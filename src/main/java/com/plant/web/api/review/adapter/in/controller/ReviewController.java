package com.plant.web.api.review.adapter.in.controller;

import com.plant.web.api.review.application.port.in.ReviewInPort;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiImplicitParams({
        @ApiImplicitParam(
            name = "contents"
            , value = "작성한 리뷰 코멘트"
            , required = true
            , dataType = "string"
        )
        , @ApiImplicitParam(
            name = "keywords"
            , value = "선택한 키워드들의 keywordId. 콤마로 구분 (ex. 1,3,4)"
            , required = true
            , dataType = "List<Long>"
        )
    })
    public ResponseEntity<?> addReview(@PathVariable(value = "memberId") String memberId, @RequestParam("placeId") String placeId, @RequestParam("contents") String contents, @RequestParam("keywords") List<Long> keywords) {
        Review result = reviewInPort.addReview(memberId, placeId, contents, keywords);
        return ResponseEntity.ok(result);
    }

    /**
     * 리뷰 수정
     * @param memberId
     * @param placeId
     * @param contents
     * @return
     */
    @PutMapping(value = "/{memberId}")
    @Operation(summary = "리뷰 수정")
    @ApiImplicitParam(
        name = "contents"
        , value = "작성한 리뷰 코멘트"
        , required = true
        , dataType = "string"
    )
    public ResponseEntity<?> modifyReview(@PathVariable(value = "memberId") String memberId, @RequestParam("placeId") String placeId, @RequestParam("contents") String contents) {
        Review result = reviewInPort.modifyReview(memberId, placeId, contents);
        return ResponseEntity.ok(result);
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    @DeleteMapping
    @Operation(summary = "리뷰 삭제")
    public ResponseEntity<?> deleteReview(@RequestParam("reviewId") Long reviewId) {
        Long result = reviewInPort.deleteReview(reviewId);
        return ResponseEntity.ok(result);
    }
}
