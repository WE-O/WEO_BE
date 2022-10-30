package com.plant.web.api.member.application.port.in;

import com.plant.web.api.member.domain.Member;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface MemberInPort {
    Member join(String accessToken, String snsType);

    Long accountRemove(String memberId);

    Member findByMemberId(String memberId);

    Long modifyNickname(String memberId, String nickname);

    List findBookmarksByMemberId(String memberId);

    Long modifyBookmark(String memberId, String bookmarkId, String memo);

    List findReviewsByMemberId(String memberId);
}
