package com.plant.web.api.review.application.port.in;

import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewInPort {
    List findReviewsByMemberId(String memberId);

    Review addReview(String memberId, String placeId, String contents, List<Long> keywords, List<MultipartFile> imgFiles);

    Review modifyReview(String memberId, String placeId, String contents);

    Long deleteReview(Long reviewId);
}
