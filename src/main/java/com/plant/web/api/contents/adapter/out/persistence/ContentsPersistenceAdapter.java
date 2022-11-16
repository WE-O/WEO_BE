package com.plant.web.api.contents.adapter.out.persistence;

import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import com.plant.web.api.contents.domain.Contents;
import com.plant.web.api.contents.domain.QContents;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
public class ContentsPersistenceAdapter implements ContentsPersistenceOutPort {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ContentsPersistenceAdapter(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * contentsId로 contents 조회
     * @param contentsId
     * @return
     */
    @Override
    public Contents findByContentsId(Long contentsId) {
        log.info("contentsId로 contents 조회");
        QContents C = new QContents("C");

        return queryFactory
                .select(C)
                .from(C)
                .where(C.contentsId.eq(contentsId))
                .fetchOne();
    }
}
