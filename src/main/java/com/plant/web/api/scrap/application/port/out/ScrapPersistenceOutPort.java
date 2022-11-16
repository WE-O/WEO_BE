package com.plant.web.api.scrap.application.port.out;

import com.plant.web.api.scrap.domain.Scrap;

import java.util.List;

public interface ScrapPersistenceOutPort {

    /**
     * 회원별 콘텐츠 스크랩 리스트 조회
     * @param memberId
     * @return
     */
    List findScrapsByMemberId(String memberId);

    /**
     * 스크랩 추가
     * @param scrap
     * @return
     */
    Scrap save(Scrap scrap);

    /**
     * 스크랩 삭제
     * @param scrapId
     * @return
     */
    Long deleteScrap(Long scrapId);

}
