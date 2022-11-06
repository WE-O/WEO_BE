package com.plant.web.api.scrap.application.service;

import com.plant.web.api.contents.application.port.in.ContentsInPort;
import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import com.plant.web.api.review.dto.ReviewDTO;
import com.plant.web.api.scrap.adapter.out.persistence.ScrapPersistenceAdapter;
import com.plant.web.api.scrap.application.port.in.ScrapInPort;
import com.plant.web.api.scrap.application.port.out.ScrapPersistenceOutPort;
import com.plant.web.api.scrap.dto.ScrapDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapService implements ScrapInPort {

    private final ScrapPersistenceOutPort scrapPersistenceOutPort;

    /**
     * 회원별 콘텐츠 스크랩 리스트 조회
     * @param memberId
     * @return
     */
    public List<ScrapDTO> findScrapsByMemberId(String memberId) {
        log.info(memberId + " 회원의 콘텐츠 스크랩 리스트 조회");
        List scraps = scrapPersistenceOutPort.findScrapsByMemberId(memberId);
        return scraps;
    }
}
