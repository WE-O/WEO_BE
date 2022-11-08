package com.plant.web.api.place.application.port.out;

import com.plant.web.api.place.domain.Place;
import org.json.simple.JSONObject;

import java.util.Optional;

public interface PlacePersistenceOutPort {
    JSONObject getKakaoPlace(String keyword);

    Optional<Long> countByPlaceId(String placeId);

    Place save(Place place);


    int getByViews(String placeId);

    int getByReviews(String placeId);

    Place getByPlaceId(String id);

}
