package com.plant.web.api.scrap.application.service;

import com.plant.web.api.contents.application.port.in.ContentsInPort;
import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import com.plant.web.api.contents.domain.Contents;
import com.plant.web.api.member.application.port.out.MemberPersistenceOutPort;
import com.plant.web.api.member.domain.Member;
import com.plant.web.api.review.dto.ReviewDTO;
import com.plant.web.api.scrap.adapter.out.persistence.ScrapPersistenceAdapter;
import com.plant.web.api.scrap.application.port.in.ScrapInPort;
import com.plant.web.api.scrap.application.port.out.ScrapPersistenceOutPort;
import com.plant.web.api.scrap.domain.Scrap;
import com.plant.web.api.scrap.dto.ScrapDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScrapService implements ScrapInPort {

    private final ScrapPersistenceOutPort scrapPersistenceOutPort;
    private final MemberPersistenceOutPort memberPersistenceOutPort;
    private final ContentsPersistenceOutPort contentsPersistenceOutPort;

    /**
     * 회원별 콘텐츠 스크랩 리스트 조회
     * @param memberId
     * @return
     */
    public List<ScrapDTO> findScrapsByMemberId(String memberId) {
        log.info(memberId + " 회원의 콘텐츠 스크랩 리스트 조회");
        List result = scrapPersistenceOutPort.findScrapsByMemberId(memberId);
        List<ScrapDTO> list = new ArrayList<>(result);
        for (int i = 0; i < result.size(); i++) {
            list.get(i).setIndex(i);
        }
        return list;
    }

    /**
     * 스크랩 추가
     * @param memberId
     * @param contentsId
     * @return
     */
    public Scrap addScrap(String memberId, Long contentsId) {
        log.info(memberId + " 의 " + contentsId + " 콘텐츠 스크랩");

        Member member = memberPersistenceOutPort.findByMemberId(memberId);
        Contents contents =  contentsPersistenceOutPort.findByContentsId(contentsId);

        Scrap setScrap = new Scrap(member, contents);
        setScrap.setDelYn('N');
        Scrap result = scrapPersistenceOutPort.save(setScrap);
        
        return result;
    }

    /**
     * 스크랩 삭제
     * @param scrapId
     * @return
     */
    @Override
    public Long deleteScrap(Long scrapId) {
        log.info(scrapId + " 스크랩 삭제");
        return scrapPersistenceOutPort.deleteScrap(scrapId);
    }
}
