package com.plant.web.api.scrap.application.port.in;

import java.util.List;

public interface ScrapInPort {

    List findScrapsByMemberId(String memberId);
}
