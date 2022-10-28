package com.plant.web.api.place.adapter.out.persistence;

import com.plant.web.api.place.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Optional;

public interface PlaceJpaRepository extends JpaRepository<Place, Serializable> {
    Optional<Long> countByPlaceId(String id);

    Place save(Place board);
}
