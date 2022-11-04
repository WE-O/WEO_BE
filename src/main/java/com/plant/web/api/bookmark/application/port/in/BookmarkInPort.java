package com.plant.web.api.bookmark.application.port.in;

import java.util.List;

public interface BookmarkInPort {

    List findBookmarksByMemberId(String memberId);

    Long modifyBookmark(String memberId, String bookmarkId, String memo);
}
