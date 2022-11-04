package com.plant.web.api.scrap.adapter.out.persistence;

import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import com.plant.web.api.contents.domain.QContents;
import com.plant.web.api.place.domain.QPlace;
import com.plant.web.api.review.domain.QReview;
import com.plant.web.api.review.dto.ReviewDTO;
import com.plant.web.api.scrap.application.port.out.ScrapPersistenceOutPort;
import com.plant.web.api.scrap.domain.QScrap;
import com.plant.web.api.scrap.dto.ScrapDTO;
import com.querydsl.core.types.Projections;
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
@RequiredArgsConstructor
public class ScrapPersistenceAdapter implements ScrapPersistenceOutPort {

    private final EntityManager em;

    /**
     * 회원별 콘텐츠 스크랩 리스트 조회
     * @param memberId
     * @return
     */

    public List<ScrapDTO> findScrapsByMemberId(String memberId) {
        log.info("회원별 콘텐츠 스크랩 리스트 조회");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QScrap S = new QScrap("S");
        QContents C = new QContents("C");

        return queryFactory.select(Projections.bean(ScrapDTO.class, S.scrapId, S.member.memberId, C.contentsId, C.img, C.detail))
                .from(S)
                .join(C).on(S.contents.contentsId.eq(C.contentsId))
                .where(S.member.memberId.eq(memberId))
                .fetch();
    }
}
