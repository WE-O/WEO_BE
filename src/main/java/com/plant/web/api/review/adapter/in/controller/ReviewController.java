package com.plant.web.api.review.adapter.in.controller;

import com.plant.web.api.review.application.port.in.ReviewInPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/review")
public class ReviewController {

    private final ReviewInPort reviewInPort;

}
