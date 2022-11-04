package com.plant.web.api.review.adapter.out.persistence;

import com.plant.web.api.place.domain.QPlace;
import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import com.plant.web.api.review.domain.QReview;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.domain.ReviewDTO;
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
public class ReviewPersistenceAdapter implements ReviewPersistenceOutPort {

    private final EntityManager em;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    public List<ReviewDTO> findReviewsByMemberId(String memberId) {
        log.info("회원별 리뷰 리스트 조회");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QReview R = new QReview("R");
        QPlace P = new QPlace("P");

        return queryFactory.select(Projections.bean(ReviewDTO.class, R.reviewId, R.member.memberId, P.placeName, R.contents, R.regDate))
                .from(R)
                .join(P).on(R.place.placeId.eq(P.placeId))
                .where(R.member.memberId.eq(memberId))
                .fetch();
    }

    /**
     * 리뷰 등록
     * @param review
     */
    public void saveReview(Review review) {
        log.info("리뷰 등록");
        em.persist(review);
    }
}
