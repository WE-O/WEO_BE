package com.plant.web.api.place.application.service;

import com.plant.web.api.place.application.port.in.PlaceInPort;
import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class PlaceService implements PlaceInPort {

    private final PlacePersistenceOutPort placePersistenceOutPort;

    public PlaceService(PlacePersistenceOutPort placePersistenceOutPort) {
        this.placePersistenceOutPort = placePersistenceOutPort;
    }

    @Override
    public JSONObject getPlaces(String keyword) {
        JSONObject getPlaces = savePlace(keyword);
        return getPlaces;
    }

    @Override
    public Place getPlaceDetails(String id) {
        Place place = placePersistenceOutPort.getByPlaceId(id);
        return place;
    }

    /**
     * 카카오 API 에서 받아오는 데이터가 없을경우 저장
     * */
    private JSONObject savePlace(String keyword) {
        JSONObject kakaoPlace = placePersistenceOutPort.getKakaoPlace(keyword);
        List<String> list = (List<String>) kakaoPlace.get("documents");
        JSONArray jsonArray = new JSONArray();
        JSONArray resultJsonArr = new JSONArray();
        JSONObject result = new JSONObject();
        //리팩토링
        for(int i = 0; i < list.size(); i++){
            jsonArray.add(list.get(i));
        }

        for(int i = 0; i < jsonArray.size(); i++){
            Map<String ,Object> map = (Map<String, Object>) jsonArray.get(i);
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
                board.setViews(0);
                board.setReviews(0);

//                Optional<Board> saveBoard = boardPersistenceOutPort.save(board);
                placePersistenceOutPort.save(board);
            } else {
                int view = placePersistenceOutPort.getByViews(map.get("id").toString());
                int review = placePersistenceOutPort.getByReviews(map.get("id").toString());
                Map<String ,Object> resultMap = new HashMap<>();

                resultMap.put("view", view);
                resultMap.put("review", review);
                resultMap.put("id", (map.get("id")));
                resultMap.put("place_name", (map.get("place_name")));
                resultMap.put("category_name", (map.get("category_name")));
                resultMap.put("road_address_name", (map.get("road_address_name")));

                resultJsonArr.add(resultMap);
            }
        }
        result.put("documents", resultJsonArr);
        return result;
    }
}
