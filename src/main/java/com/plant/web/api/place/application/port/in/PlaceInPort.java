package com.plant.web.api.place.application.port.in;

import org.json.simple.JSONObject;

public interface PlaceInPort {
    JSONObject getPlaces(String keyword, String loginCheck);
}
