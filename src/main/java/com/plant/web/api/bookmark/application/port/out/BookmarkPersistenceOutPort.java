package com.plant.web.api.bookmark.application.port.out;

import java.util.List;

public interface BookmarkPersistenceOutPort {

    /**
     * 회원별 북마크 리스트 조회
     * @param memberId
     * @return
     */
    List findBookmarksByMemberId(String memberId);

    /**
     * 북마크 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    Long modifyBookmark(String memberId, String bookmarkId, String memo);
}