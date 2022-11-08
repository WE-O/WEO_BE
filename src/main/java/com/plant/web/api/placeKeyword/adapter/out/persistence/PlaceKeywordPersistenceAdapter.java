package com.plant.web.api.placeKeyword.adapter.out.persistence;

import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import com.plant.web.api.review.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class PlaceKeywordPersistenceAdapter implements PlaceKeywordPersistenceOutPort {

    private final EntityManager em;
    private final PlaceKeywordJpaRepository placeKeywordJpaRepository;

    /*
    public List<Keyword> saveAll(List<Keyword> placeKeywords) {
        log.info("키워드 등록");
        //em.persist(placeKeyword);
        //return placeKeywordJpaRepository.saveAll();
    }

     */

    @Override
    public PlaceKeyword save(PlaceKeyword placeKeyword) {
        log.info("리뷰 등록");
        //em.persist(review);
        return placeKeywordJpaRepository.save(placeKeyword);
    }

}
