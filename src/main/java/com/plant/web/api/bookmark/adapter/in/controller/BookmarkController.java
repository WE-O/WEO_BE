package com.plant.web.api.bookmark.adapter.in.controller;

import com.plant.web.api.bookmark.application.port.in.BookmarkInPort;
import com.plant.web.api.bookmark.domain.Bookmark;
import com.plant.web.api.bookmark.domain.ModifyBookmarkRequest;
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
     * 마이페이지 북마크 메모 수정
     * @param memberId
     * @param bookmarkId
     * @param memo
     * @return
     */
    @PutMapping(value = "/{memberId}")
    @Operation(summary = "마이페이지 북마크 메모 수정")
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

    /**
     * 북마크 수정
     * @param request
     * @return
     */
    @PatchMapping
    @Operation(summary = "회원 북마크 수정")
    @ApiImplicitParam(
            name = "memberId,placeId,bookmarkYN"
            , value = "북마크 수정"
            , required = true
            , dataType = "Object"
    )
    public ResponseEntity<?> modifyBookmark(@RequestBody ModifyBookmarkRequest request) {
        Bookmark modifyBookmark = bookmarkInPort.modifyBookmark(request);

        return ResponseEntity.ok(modifyBookmark);
    }

    /**
     * 북마크 삭제
     * @param memberId
     * @param bookmarks
     * @return
     */
    @DeleteMapping(value = "/{memberId}")
    @Operation(summary = "북마크 삭제")
    @ApiImplicitParam(
        name = "bookmarks"
        , value = "삭제할 bookmarkId 리스트. 콤마로 구분 (ex. 1,2)"
        , required = true
        , dataType = "List<Long>"
    )
    public ResponseEntity<?> deleteBookmark(@PathVariable(value = "memberId") String memberId, @RequestParam("bookmarks") List<Long> bookmarks) {
        Long result = bookmarkInPort.deleteBookmark(memberId, bookmarks);
        return ResponseEntity.ok(result);
    }
}
