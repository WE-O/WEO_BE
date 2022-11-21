package com.plant.web.api.placeKeyword.dto;

import lombok.Data;

@Data
public class PlaceKeywordDTO {

    private String placeId;

    private String placeName;

    private Long keywordId;

    private String keywordName;

    private Long cnt;
}
