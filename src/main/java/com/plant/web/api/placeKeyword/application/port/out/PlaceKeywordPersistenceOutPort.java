package com.plant.web.api.placeKeyword.application.port.out;

import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import com.plant.web.api.placeKeyword.dto.PlaceKeywordDTO;

import java.util.List;

public interface PlaceKeywordPersistenceOutPort {

    /**
     * 키워드 등록
     * @param placeKeyword
     */
    PlaceKeyword save(PlaceKeyword placeKeyword);

    /**
     * 식물집사가 선택한 키워드
     * @param placeId
     * @return
     */
    List<PlaceKeywordDTO> getKeywordList(String placeId);
}
