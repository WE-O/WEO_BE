package com.plant.web.api.place.application.port.in;

import com.plant.web.api.place.domain.Place;
import org.json.simple.JSONObject;

public interface PlaceInPort {
    JSONObject getPlaces(String keyword);

    Place getPlaceDetails(String id);

}
