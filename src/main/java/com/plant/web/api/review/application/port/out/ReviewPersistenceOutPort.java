package com.plant.web.api.review.application.port.out;

import java.util.List;

public interface ReviewPersistenceOutPort {

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    List findReviewsByMemberId(String memberId);
}