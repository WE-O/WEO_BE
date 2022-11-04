package com.plant.web.api.bookmark.adapter.out.persistence;

import com.plant.web.api.bookmark.application.port.out.BookmarkPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class BookmarkPersistenceAdapter implements BookmarkPersistenceOutPort {

    private final EntityManager em;
}
