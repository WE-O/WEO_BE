package com.plant.web.api.placeKeyword.application.service;

import com.plant.web.api.placeKeyword.application.port.in.PlaceKeywordInPort;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import com.plant.web.api.placeKeyword.dto.PlaceKeywordDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceKeywordService implements PlaceKeywordInPort {

    private final PlaceKeywordPersistenceOutPort placeKeywordPersistenceOutPort;

    @Override
    public List<PlaceKeywordDTO> getKeywordList(String placeId) {
        log.info(placeId + " 장소의 식물집사가 선택한 키워드");
        List<PlaceKeywordDTO> result = placeKeywordPersistenceOutPort.getKeywordList(placeId);
        return result;
    }
}
