package com.plant.web.api.contents.adapter.in.controller;

import com.plant.web.api.contents.application.port.in.ContentsInPort;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/contents")
@Api(tags = {"콘텐츠 관련 API"})
public class ContentsController {

    //private final ContentsInPort contentsInPort;

}
