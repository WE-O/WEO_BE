package com.plant.web.api.review.application.service;

import com.plant.web.api.keyword.application.port.out.KeywordPersistenceOutPort;
import com.plant.web.api.keyword.domain.Keyword;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import com.plant.web.api.review.application.port.in.ReviewInPort;
import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import com.plant.web.api.review.domain.Review;
import com.plant.web.api.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService implements ReviewInPort {

    private final ReviewPersistenceOutPort reviewPersistenceOutPort;
    private final MemberPersistenceOutPort memberPersistenceOutPort;
    private final PlacePersistenceOutPort placePersistenceOutPort;
    private final KeywordPersistenceOutPort keywordPersistenceOutPort;
    private final PlaceKeywordPersistenceOutPort placeKeywordPersistenceOutPort;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    public List<ReviewDTO> findReviewsByMemberId(String memberId) {
        log.info(memberId + " 회원의 리뷰 리스트 조회");
        List reviews = reviewPersistenceOutPort.findReviewsByMemberId(memberId);
        return reviews;
    }

    /**
     * 리뷰 등록
     * @param memberId
     * @param placeId
     * @param contents
     * @param keywords
     * @return
     */
    public Review addReview(String memberId, String placeId, String contents, List<Long> keywords) {
        log.info(memberId + " 회원의 " + placeId + " 장소에 대한 리뷰 및 키워드 등록");

        Member member = memberPersistenceOutPort.findByMemberId(memberId);
        Place place = placePersistenceOutPort.getByPlaceId(placeId);

        Review setReview = new Review(member, place, contents);
        Review result = reviewPersistenceOutPort.save(setReview);

        //키워드 등록
        //List<PlaceKeyword> placeKeywords = new ArrayList<>();
        keywords.forEach(item -> {
            Keyword keyword = keywordPersistenceOutPort.findByKeywordId(item);
            PlaceKeyword placeKeyword = new PlaceKeyword(place, keyword);
            //placeKeywords.add(placeKeyword);
            placeKeywordPersistenceOutPort.save(placeKeyword);
        });

        return result;
    }

    /**
     * 리뷰 수정
     * @param memberId
     * @param placeId
     * @param contents
     * @return
     */
    public Review modifyReview(String memberId, String placeId, String contents) {
        log.info(memberId + " 회원의 " + placeId + " 장소에 대한 리뷰 수정");

        Member member = memberPersistenceOutPort.findByMemberId(memberId);
        Place place = placePersistenceOutPort.getByPlaceId(placeId);

        Review setReview = new Review(member, place, contents);
        Review result = reviewPersistenceOutPort.save(setReview);

        return result;
    }

}
