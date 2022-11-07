package com.plant.web.api.placeKeyword.application.port.out;

import com.plant.web.api.placeKeyword.domain.PlaceKeyword;

public interface PlaceKeywordPersistenceOutPort {

    /**
     * 키워드 등록
     * @param placeKeyword
     */
    void savePlaceKeyword(PlaceKeyword placeKeyword);
}
