package com.plant.web.api.bookmark.adapter.out.persistence;

import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import com.plant.web.api.bookmark.domain.Bookmark;
import com.plant.web.api.bookmark.dto.BookmarkDTO;
import com.plant.web.api.bookmark.domain.QBookmark;
import com.plant.web.api.member.domain.QMember;
import com.plant.web.api.place.domain.QPlace;
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
public class BookmarkPersistenceAdapter implements BookmarkPersistenceOutPort {

    private final EntityManager em;
    private final BookmarkJpaRepository bookmarkJpaRepository;

    private final JPAQueryFactory queryFactory;

    public BookmarkPersistenceAdapter(EntityManager em, BookmarkJpaRepository bookmarkJpaRepository) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.bookmarkJpaRepository = bookmarkJpaRepository;
    }

    /**
     * 회원별 북마크 리스트 조회
     * @param memberId
     * @return
     */

    public List<BookmarkDTO> findBookmarksByMemberId(String memberId) {
        log.info("회원별 북마크 리스트 조회");
        QBookmark B = new QBookmark("B");
        QPlace P = new QPlace("P");

        return queryFactory.select(Projections.bean(BookmarkDTO.class, B.bookmarkId, B.member.memberId, P.placeId, P.placeName, P.roadAddressName, B.memo, B.regDate))
                .from(B)
                .join(P).on(B.place.placeId.eq(P.placeId))
                .where(B.member.memberId.eq(memberId))
                .orderBy(B.regDate.desc())
                .fetch();
    }

    /**
     * 북마크 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    public Long modifyBookmark(String memberId, Long bookmarkId, String memo) {
        log.info("북마크 수정");
        QBookmark B = new QBookmark("B");

        return queryFactory.update(B)
                .set(B.memo, memo)
                .where(B.member.memberId.eq(memberId)
                        .and(B.bookmarkId.eq(bookmarkId)))
                .execute();
    }

    @Override
    public String getBookmarkByBookmarkYN(String placeId, String memberId) {
        return bookmarkJpaRepository.getBookmarkByBookmarkYN(placeId, memberId);
    }

    /**
     * 북마크 삭제
     * @param bookmarkId
     * @return
     */
    @Override
    public Long deleteBookmark(Long bookmarkId) {
        log.info(bookmarkId + " 삭제");
        QBookmark B = new QBookmark("B");

        return queryFactory.update(B)
                .set(B.bookmarkYN, "N")
                .where(B.bookmarkId.eq(bookmarkId))
                .execute();
    }
}
