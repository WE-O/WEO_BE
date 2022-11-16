package com.plant.web.api.scrap.adapter.out.persistence;

import com.plant.web.api.scrap.domain.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface ScrapJpaRepository extends JpaRepository<Scrap, Serializable> {

}
