package com.plant.web.api.review.dto;

import com.plant.web.api.place.domain.Place;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private String reviewId;

    private String memberId;

    private String placeName;

    private String contents;

    private LocalDateTime regDate;
}
