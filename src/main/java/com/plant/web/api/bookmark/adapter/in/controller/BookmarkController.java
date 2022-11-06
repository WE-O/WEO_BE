package com.plant.web.api.bookmark.adapter.in.controller;

import com.plant.web.api.bookmark.application.port.in.BookmarkInPort;
import com.plant.web.api.member.application.port.in.MemberInPort;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/bookmark")
@Api(tags = {"북마크(장소저장) 관련 API"})
public class BookmarkController {

    private final BookmarkInPort bookmarkInPort;

    /**
     * 회원별 북마크 리스트 조회
     * @param memberId
     * @return
     */
    @GetMapping(value = "/{memberId}")
    @Operation(summary = "회원별 북마크 리스트 조회")
    public ResponseEntity<?> findBookmarksByMemberId(@PathVariable(value = "memberId") String memberId) {
        List bookmarks = bookmarkInPort.findBookmarksByMemberId(memberId);
        return ResponseEntity.ok(bookmarks);
    }

    /**
     * 북마크 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    @PutMapping(value = "/{memberId}")
    @Operation(summary = "회원별 북마크 수정")
    @ApiImplicitParam(
            name = "memo"
            , value = "수정할 메모 내용"
            , required = true
            , dataType = "string"
    )
    public ResponseEntity<?> modifyBookmark(
            @PathVariable(value = "memberId") String memberId
            , @RequestParam("bookmarkId") Long bookmarkId
            , @RequestParam("memo") String memo) {

        Long modifyBookmark = bookmarkInPort.modifyBookmark(memberId, bookmarkId, memo);
        return ResponseEntity.ok(modifyBookmark);
    }
}
