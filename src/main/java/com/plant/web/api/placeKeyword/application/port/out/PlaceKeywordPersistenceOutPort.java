package com.plant.web.api.placeKeyword.application.port.out;

import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;

import java.util.List;

public interface PlaceKeywordPersistenceOutPort {

    /**
     * 키워드 등록
     * @param placeKeyword
     */
    //List<Keyword> saveAll(List<Keyword> placeKeywords);
    PlaceKeyword save(PlaceKeyword placeKeyword);
}
