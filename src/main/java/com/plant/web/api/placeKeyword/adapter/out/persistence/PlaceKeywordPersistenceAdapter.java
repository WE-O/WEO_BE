package com.plant.web.api.placeKeyword.adapter.out.persistence;

import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.placeKeyword.application.port.in.PlaceKeywordInPort;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PlaceKeywordPersistenceAdapter implements PlaceKeywordPersistenceOutPort {

    private final EntityManager em;

    public void savePlaceKeyword(PlaceKeyword placeKeyword) {
        log.info("키워드 등록");
        em.persist(placeKeyword);
    }
}
