package com.plant.web.api.bookmark.application.port.in;

import com.plant.web.api.bookmark.domain.Bookmark;

import java.util.List;

public interface BookmarkInPort {

    List findBookmarksByMemberId(String memberId);

    Long modifyBookmark(String memberId, Long bookmarkId, String memo);

    Long deleteBookmark(String memberId, List<Long> bookmarks);
}
