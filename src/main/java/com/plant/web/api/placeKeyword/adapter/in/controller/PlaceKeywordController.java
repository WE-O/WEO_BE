package com.plant.web.api.placeKeyword.adapter.in.controller;

import com.plant.web.api.placeKeyword.application.port.in.PlaceKeywordInPort;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/keyword")
@Api(tags = {"키워드 API"})
public class PlaceKeywordController {

    private final PlaceKeywordInPort placeKeywordInPort;


}
