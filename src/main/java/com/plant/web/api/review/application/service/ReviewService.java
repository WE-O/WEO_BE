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
import com.plant.web.config.bucket.S3Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private final S3Image s3Image;

    /**
     * 회원별 리뷰 리스트 조회
     * @param memberId
     * @return
     */
    public List<ReviewDTO> findReviewsByMemberId(String memberId) {
        log.info(memberId + " 회원의 리뷰 리스트 조회");
        List result = reviewPersistenceOutPort.findReviewsByMemberId(memberId);
        List<ReviewDTO> list = new ArrayList<>(result);

        for (int i = 0; i < result.size(); i++) {
            list.get(i).setIndex(i);
        }
        return list;
    }

    /**
     * 리뷰 등록
     * @param memberId
     * @param placeId
     * @param contents
     * @param keywords
     * @return
     */
    public Review addReview(String memberId, String placeId, String contents, List<Long> keywords, List<MultipartFile> imgFiles) {
        log.info(memberId + " 회원의 " + placeId + " 장소에 대한 리뷰 및 키워드 등록");

        Member member = memberPersistenceOutPort.findByMemberId(memberId);
        Place place = placePersistenceOutPort.getByPlaceId(placeId);

        Review setReview = new Review(member, place, contents);
        setReview.setDelYn('N');
        Review result = reviewPersistenceOutPort.save(setReview);

        //키워드 등록
        //List<PlaceKeyword> placeKeywords = new ArrayList<>();
        keywords.forEach(item -> {
            Keyword keyword = keywordPersistenceOutPort.findByKeywordId(item);
            PlaceKeyword placeKeyword = new PlaceKeyword(place, keyword);
            //placeKeywords.add(placeKeyword);
            placeKeywordPersistenceOutPort.save(placeKeyword);
        });

        //사진 등록
        //s3Image.s3Image(imgFiles);

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

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    public Long deleteReview(Long reviewId) {
        log.info(reviewId + "리뷰 삭제");
        return reviewPersistenceOutPort.deleteReview(reviewId);
    }

}
