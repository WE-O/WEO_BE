package com.plant.web.api.scrap.adapter.out.persistence;

import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import com.plant.web.api.contents.domain.QContents;
import com.plant.web.api.place.domain.QPlace;
import com.plant.web.api.review.domain.QReview;
import com.plant.web.api.review.dto.ReviewDTO;
import com.plant.web.api.scrap.application.port.out.ScrapPersistenceOutPort;
import com.plant.web.api.scrap.domain.QScrap;
import com.plant.web.api.scrap.domain.Scrap;
import com.plant.web.api.scrap.dto.ScrapDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Repository
@Transactional
public class ScrapPersistenceAdapter implements ScrapPersistenceOutPort {

    private final EntityManager em;
    private final ScrapJpaRepository scrapJpaRepository;
    private final JPAQueryFactory queryFactory;

    public ScrapPersistenceAdapter(EntityManager em, ScrapJpaRepository scrapJpaRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.scrapJpaRepository = scrapJpaRepository;
    }

    /**
     * 회원별 콘텐츠 스크랩 리스트 조회
     * @param memberId
     * @return
     */

    public List<ScrapDTO> findScrapsByMemberId(String memberId) {
        log.info("회원별 콘텐츠 스크랩 리스트 조회");
        QScrap S = new QScrap("S");
        QContents C = new QContents("C");

        return queryFactory.select(Projections.bean(ScrapDTO.class, S.scrapId, C.contentsId, C.title, C.img, S.regDate))
                .from(S)
                .join(C).on(S.contents.contentsId.eq(C.contentsId))
                .where(S.member.memberId.eq(memberId).and(S.delYn.eq('N')))
                .orderBy(S.regDate.desc())
                .fetch();
    }

    /**
     * 스크랩 추가
     * @param scrap
     * @return
     */
    @Override
    public Scrap save(Scrap scrap) {
        log.info("스크랩 추가");
        return scrapJpaRepository.save(scrap);
    }

    /**
     * 스크랩 삭제
     * @param scrapId
     * @return
     */
    @Override
    public Long deleteScrap(Long scrapId) {
        log.info(scrapId + " 삭제");
        QScrap S = new QScrap("S");

        return queryFactory.update(S)
                .set(S.delYn, 'Y')
                .set(S.updDate, LocalDateTime.now())
                .where(S.scrapId.eq(scrapId))
                .execute();
    }
}
