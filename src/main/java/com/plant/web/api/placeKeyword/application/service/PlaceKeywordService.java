package com.plant.web.api.placeKeyword.application.service;

import com.plant.web.api.placeKeyword.application.port.in.PlaceKeywordInPort;
import com.plant.web.api.placeKeyword.application.port.out.PlaceKeywordPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlaceKeywordService implements PlaceKeywordInPort {

    private final PlaceKeywordPersistenceOutPort placeKeywordPersistenceOutPort;


}
