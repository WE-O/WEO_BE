package com.plant.web.api.bookmark.application.service;

import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkPersistenceOutPort bookmarkPersistenceOutPort;
}
