package com.plant.web.api.review.application.service;

import com.plant.web.api.review.application.port.in.ReviewInPort;
import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService implements ReviewInPort {

    private final ReviewPersistenceOutPort reviewPersistenceOutPort;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    public List<ReviewDTO> findReviewsByMemberId(String memberId) {
        log.info(memberId + " 회원의 리뷰 리스트 조회");
        List reviews = reviewPersistenceOutPort.findReviewsByMemberId(memberId);
        return reviews;
    }

    /**
     * 리뷰 등록
     * @param memberId
     * @param placeId
     * @param contents
     * @param keywords
     * @return
     */
    public Review addReview(String memberId, String placeId, String contents, List<Long> keywords) {
        log.info(memberId + " 회원의 " + placeId + " 장소에 대한 리뷰 등록");

        Review setReview = new Review();
        //setReview.setMember(memberId);
        //setReview.setPlaceId(placeId);
        setReview.setContents(contents);

        //reviewPersistenceOutPort.saveReview(setReview);

        return setReview;
    }

}
