package com.plant.web.api.review.adapter.out.persistence;

import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ReviewPersistenceAdapter implements ReviewPersistenceOutPort {

    private final EntityManager em;
}
