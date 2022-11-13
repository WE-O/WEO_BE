package com.plant.web.api.bookmark.application.service;

import com.plant.web.api.bookmark.adapter.out.persistence.BookmarkJpaRepository;
import com.plant.web.api.bookmark.application.port.in.BookmarkInPort;
import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import com.plant.web.api.bookmark.domain.Bookmark;
import com.plant.web.api.bookmark.domain.ModifyBookmarkRequest;
import com.plant.web.api.bookmark.dto.BookmarkDTO;
import com.plant.web.api.member.adapter.out.persistence.MemberJpaRepository;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.place.adapter.out.persistence.PlaceJpaRepository;
import com.plant.web.api.place.domain.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookmarkService implements BookmarkInPort {

    private final BookmarkJpaRepository bookmarkJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final PlaceJpaRepository placeJpaRepository;
    private final BookmarkPersistenceOutPort bookmarkPersistenceOutPort;

    /**
     * 회원별 북마크 조회
     * @param memberId
     * @return
     */
    public List<BookmarkDTO> findBookmarksByMemberId(String memberId) {
        log.info(memberId + " 회원의 북마크 조회");
        List result = bookmarkPersistenceOutPort.findBookmarksByMemberId(memberId);
        List<BookmarkDTO> list = new ArrayList<>(result);

        for(int i = 0; i < result.size(); i++){
            list.get(i).setIndex(i);
        }

        return list;
    }

    /**
     * 마이페이지 북마크 메모 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    public Long modifyBookmark(String memberId, Long bookmarkId, String memo) {
        log.info("북마크 메모 수정");
        return bookmarkPersistenceOutPort.modifyBookmark(memberId, bookmarkId, memo);
    }

    @Override
    public Bookmark modifyBookmark(ModifyBookmarkRequest request) {
        Member member = memberJpaRepository.getByMemberId(request.getMemberId());
        Place place = placeJpaRepository.getByPlaceId(request.getPlaceId());

        Bookmark bookmark = bookmarkJpaRepository.findByMemberAndPlace(member, place);
        bookmark.setBookmarkYN(request.getBookmarkYN());
        return bookmarkJpaRepository.save(bookmark);
    }

    public Bookmark findByMemberAndPlace(Member member, Place place) {
        return bookmarkJpaRepository.findByMemberAndPlace(member, place);
    }

    /**
     * 북마크 삭제
     * @param memberId
     * @param bookmarks
     * @return
     */
    public Long deleteBookmark(String memberId, List<Long> bookmarks) {
        log.info(memberId + " 회원의 북마크 삭제");
        Long result = 0L;

        for(int i=0; i<bookmarks.size(); i++) {
            Long delResult = bookmarkPersistenceOutPort.deleteBookmark(bookmarks.get(i));
            if(delResult > 0) {
                result = 1L;
            } else {
                return 0L;
            }
        }

        return result;
    }


}
