package com.plant.web.api.placeKeyword.adapter.out.persistence;

import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.keyword.domain.QKeyword;
import com.plant.web.api.place.domain.QPlace;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import com.plant.web.api.placeKeyword.domain.QPlaceKeyword;
import com.plant.web.api.placeKeyword.dto.PlaceKeywordDTO;
import com.plant.web.api.review.domain.Review;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class PlaceKeywordPersistenceAdapter implements PlaceKeywordPersistenceOutPort {

    private final EntityManager em;
    private final PlaceKeywordJpaRepository placeKeywordJpaRepository;
    private final JPAQueryFactory queryFactory;

    public PlaceKeywordPersistenceAdapter(EntityManager em, PlaceKeywordJpaRepository placeKeywordJpaRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.placeKeywordJpaRepository = placeKeywordJpaRepository;
    }

    /**
     * 리뷰에 대한 키워드 등록
     * @param placeKeyword
     * @return
     */
    @Override
    public PlaceKeyword save(PlaceKeyword placeKeyword) {
        log.info("리뷰에 대한 키워드 등록");
        return placeKeywordJpaRepository.save(placeKeyword);
    }

    /**
     * 식물집사가 선택한 키워드
     * @param placeId
     * @return
     */
    @Override
    public List<PlaceKeywordDTO> getKeywordList(String placeId) {
        log.info("식물집사가 선택한 키워드");
        QPlaceKeyword PK = new QPlaceKeyword("PK");
        QPlace P = new QPlace("P");
        QKeyword K = new QKeyword("K");

        return queryFactory.select(Projections.bean(PlaceKeywordDTO.class, P.placeId, P.placeName, K.keywordId, K.keywordName, Wildcard.count.as("cnt")))
                .from(PK)
                .join(P).on(PK.place.placeId.eq(P.placeId))
                .join(K).on(PK.keyword.keywordId.eq(K.keywordId))
                .where(P.placeId.eq(placeId))
                .groupBy(P.placeId, P.placeName, K.keywordId, K.keywordName)
                .orderBy(Wildcard.count.desc(), K.keywordName.desc())
                .fetch();
    }

}
