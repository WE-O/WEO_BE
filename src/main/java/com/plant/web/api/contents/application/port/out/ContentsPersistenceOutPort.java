package com.plant.web.api.contents.application.port.out;

import com.plant.web.api.contents.domain.Contents;

import java.util.List;

public interface ContentsPersistenceOutPort {

    /**
     * contentsId로 contents 확인
     * @param contentsId
     * @return
     */
    Contents findByContentsId(Long contentsId);
}
