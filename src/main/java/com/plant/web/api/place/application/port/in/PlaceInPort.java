package com.plant.web.api.place.application.port.in;

import com.plant.web.api.place.dto.PlaceDto;
import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface PlaceInPort {
    JSONObject getPlaces(String keyword, String memberId);

    PlaceDto.Response getPlaceDetails(String placeId, HttpServletRequest request, HttpServletResponse response);
}
