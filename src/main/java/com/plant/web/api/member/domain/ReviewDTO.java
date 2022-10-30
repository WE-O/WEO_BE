package com.plant.web.api.member.domain;

import com.plant.web.api.place.domain.Place;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {

    private String reviewId;

    private Member memberId;

    private String placeName;

    private String comment;

    private LocalDateTime regDate;
}
