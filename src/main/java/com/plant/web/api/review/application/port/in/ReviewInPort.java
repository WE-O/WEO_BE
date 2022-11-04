package com.plant.web.api.review.application.port.in;

import java.util.List;

public interface ReviewInPort {
    List findReviewsByMemberId(String memberId);
}
