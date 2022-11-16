package com.plant.web.api.scrap.application.port.in;

import com.plant.web.api.scrap.domain.Scrap;

import java.util.List;

public interface ScrapInPort {

    List findScrapsByMemberId(String memberId);

    Scrap addScrap(String memberId, Long contentsId);

    Long deleteScrap(Long scrapId);
}
