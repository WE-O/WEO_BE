package com.plant.web.api.review.application.port.out;

import com.plant.web.api.member.domain.Member;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewPersistenceOutPort {

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    List findReviewsByMemberId(String memberId);

    /**
     * 리뷰 등록
     */
    Review save(Review review);
}
