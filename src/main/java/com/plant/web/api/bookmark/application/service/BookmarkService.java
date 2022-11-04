package com.plant.web.api.bookmark.application.service;

import com.plant.web.api.bookmark.application.port.in.BookmarkInPort;
import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import com.plant.web.api.bookmark.domain.Bookmark;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookmarkService implements BookmarkInPort {

    private final BookmarkPersistenceOutPort bookmarkPersistenceOutPort;

    /**
     * 회원별 북마크 조회
     * @param memberId
     * @return
     */
    public List<Bookmark> findBookmarksByMemberId(String memberId) {
        log.info(memberId + " 회원의 북마크 조회");
        List bookmarks = bookmarkPersistenceOutPort.findBookmarksByMemberId(memberId);
        return bookmarks;
    }

    /**
     * 북마크 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    public Long modifyBookmark(String memberId, String bookmarkId, String memo) {
        log.info("북마크 수정");
        return bookmarkPersistenceOutPort.modifyBookmark(memberId, bookmarkId, memo);
    }
}
