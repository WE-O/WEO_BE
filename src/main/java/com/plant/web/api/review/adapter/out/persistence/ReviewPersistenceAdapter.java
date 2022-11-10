package com.plant.web.api.review.adapter.out.persistence;

import com.plant.web.api.bookmark.domain.QBookmark;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.adapter.out.persistence.PlaceJpaRepository;
import com.plant.web.api.place.domain.Place;
import com.plant.web.api.place.domain.QPlace;
import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import com.plant.web.api.review.domain.QReview;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
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
    private final ReviewJpaRepository reviewJpaRepository;

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

        return queryFactory.select(Projections.bean(ReviewDTO.class, R.reviewId, R.member.memberId, P.placeId, P.placeName, R.contents, R.regDate))
                .from(R)
                .join(P).on(R.place.placeId.eq(P.placeId))
                .where(R.member.memberId.eq(memberId).and(R.delYn.eq('N')))
                .fetch();
    }

    /**
     * 리뷰등록/수정
     * @param review
     */
    @Override
    public Review save(Review review) {
        log.info("리뷰 등록/수정");
        return reviewJpaRepository.save(review);
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    @Override
    public Long deleteReview(Long reviewId) {
        log.info(reviewId + " 삭제");
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QReview R = new QReview("R");
        Review review = new Review();

        return queryFactory.update(R)
                .set(R.delYn, 'Y')
                .set(R.updDate, review.getUpdDate())
                .where(R.reviewId.eq(reviewId))
                .execute();
    }
}
