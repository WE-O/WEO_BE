package com.plant.web.api.review.application.port.in;

import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;

import java.util.List;

public interface ReviewInPort {
    List findReviewsByMemberId(String memberId);

    Review addReview(String memberId, String placeId, String contents, List<Long> keywords);

    Review modifyReview(String memberId, String placeId, String contents);
}
