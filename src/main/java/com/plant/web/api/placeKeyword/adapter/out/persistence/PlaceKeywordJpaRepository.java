package com.plant.web.api.placeKeyword.adapter.out.persistence;

import com.plant.web.api.placeKeyword.domain.PlaceKeyword;
import com.plant.web.api.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;


public interface PlaceKeywordJpaRepository extends JpaRepository<PlaceKeyword, Serializable> {

}
