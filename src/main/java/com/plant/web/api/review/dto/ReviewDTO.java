package com.plant.web.api.review.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDTO {

    private Long reviewId;

    private String memberId;

    private String placeId;

    private String placeName;

    private String contents;

    private LocalDateTime regDate;
}
