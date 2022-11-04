package com.plant.web.api.review.application.service;

import com.plant.web.api.review.application.port.out.ReviewPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewPersistenceOutPort reviewPersistenceOutPort;

}
