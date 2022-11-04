package com.plant.web.api.bookmark.adapter.in.controller;

import com.plant.web.api.bookmark.application.port.in.BookmarkInPort;
import com.plant.web.api.member.application.port.in.MemberInPort;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/bookmark")
@Api(tags = {"북마크(장소저장) 관련 API"})
public class BookmarkController {

    private final BookmarkInPort bookmarkInPort;
}
