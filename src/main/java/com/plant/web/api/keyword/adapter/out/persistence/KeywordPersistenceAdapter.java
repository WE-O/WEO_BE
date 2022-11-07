package com.plant.web.api.keyword.adapter.out.persistence;

import com.plant.web.api.keyword.application.port.out.KeywordPersistenceOutPort;
import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.keyword.domain.QKeyword;
import com.plant.web.api.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class KeywordPersistenceAdapter implements KeywordPersistenceOutPort {

    private final EntityManager em;

    /**
     * keywordId로 키워드 조회
     * @param keywordId
     * @return
     */
    public Keyword findByKeywordId(Long keywordId) {
        log.info("keywordId로 키워드 조회");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QKeyword K = new QKeyword("k");

        return queryFactory
                .select(K)
                .from(K)
                .where(K.keywordId.eq(keywordId))
                .fetchOne();
    }
}
