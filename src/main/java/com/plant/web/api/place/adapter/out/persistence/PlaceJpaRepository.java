package com.plant.web.api.place.adapter.out.persistence;

import com.plant.web.api.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Optional;

public interface PlaceJpaRepository extends JpaRepository<Place, Serializable> {
    Optional<Long> countByPlaceId(String placeId);

    Place save(Place board);

    @Query("SELECT views FROM Place WHERE placeId = :placeId")
    int getByViews(@Param("placeId") String placeId);

    @Query("SELECT reviews FROM Place WHERE placeId = :placeId")
    int getByReviews(@Param("placeId") String placeId);

    Place getByPlaceId(String placeId);

}
