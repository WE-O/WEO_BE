package com.plant.web.api.place.adapter.out.persistence;

import com.plant.web.api.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Optional;

public interface PlaceJpaRepository extends JpaRepository<Place, Serializable> {
    Optional<Long> countByPlaceId(String id);

    Place save(Place board);

    @Query("SELECT views FROM Place WHERE placeId = :placeId")
    int findByViews(@Param("placeId") String id);

    @Query("SELECT reviews FROM Place WHERE placeId = :placeId")
    int findByReviews(@Param("placeId") String id);

}
