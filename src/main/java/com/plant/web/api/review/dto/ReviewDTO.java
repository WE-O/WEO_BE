package com.plant.web.api.review.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDTO {

    private int index;

    private Long reviewId;

    private String memberId;

    private String placeId;

    private String placeName;

    private String contents;

    private LocalDateTime regDate;

    private LocalDateTime updDate;

}
