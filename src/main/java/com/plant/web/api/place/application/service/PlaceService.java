package com.plant.web.api.place.application.service;

import com.plant.web.api.place.application.port.in.PlaceInPort;
import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class PlaceService implements PlaceInPort {

    private final PlacePersistenceOutPort placePersistenceOutPort;

    public PlaceService(PlacePersistenceOutPort placePersistenceOutPort) {
        this.placePersistenceOutPort = placePersistenceOutPort;
    }

    @Override
    public JSONObject getPlaces(String keyword) {
        JSONObject kakaoPlace = placePersistenceOutPort.getKakaoPlace(keyword);
        List<String> list = (List<String>) kakaoPlace.get("documents");
        JSONArray jsonArray = new JSONArray();
        //리팩토링
        for(int i = 0; i < list.size(); i++){
            jsonArray.add(list.get(i));
        }

        for(int i = 0; i < jsonArray.size(); i++){
            java.util.Map<String ,Object> map = (java.util.Map<String, Object>) jsonArray.get(i);
            Optional<Long> idIndex = placePersistenceOutPort.countByPlaceId(map.get("id").toString());

            if(idIndex.get() < 1){
                Place board = new Place();
                board.setAddressName((String) map.get("address_name"));
                board.setCategoryGroupCode((String) map.get("category_group_code"));
                board.setCategoryGroupName((String) map.get("category_group_name"));
                board.setCategoryGroupName((String) map.get("category_name"));
                board.setDistance((String) map.get("distance"));
                board.setPlaceId((String) map.get("id"));
                board.setPhone((String) map.get("phone"));
                board.setPlaceName((String) map.get("place_name"));
                board.setPlaceUrl((String) map.get("place_url"));
                board.setRoadAddressName((String) map.get("road_address_name"));
                board.setX((String) map.get("x"));
                board.setY((String) map.get("y"));

//                Optional<Board> saveBoard = boardPersistenceOutPort.save(board);
                placePersistenceOutPort.save(board);
            }
        }




        return kakaoPlace;
    }
}
