package com.plant.web.api.contents.adapter.out.persistence;

import com.plant.web.api.contents.application.port.out.ContentsPersistenceOutPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Repository
@Transactional
@RequiredArgsConstructor
public class ContentsPersistenceAdapter{

    private final EntityManager em;

}
