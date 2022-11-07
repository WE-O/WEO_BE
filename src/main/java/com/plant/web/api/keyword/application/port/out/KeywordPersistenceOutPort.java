package com.plant.web.api.keyword.application.port.out;

import com.plant.web.api.keyword.domain.Keyword;

public interface KeywordPersistenceOutPort {

    /**
     * keywordId로 키워드 확인
     * @param keywordId
     * @return
     */
    Keyword findByKeywordId(Long keywordId);
}
