package com.plant.web.api.place.application.service;

import com.plant.web.api.place.application.port.in.PlaceInPort;
import com.plant.web.api.place.application.port.out.PlacePersistenceOutPort;
import com.plant.web.api.place.domain.Place;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    public Place getPlaceDetails(String id, HttpServletRequest request, HttpServletResponse response) {
        Place outPortByPlace = placePersistenceOutPort.getByPlaceId(id);
        Cookie[] cookies = request.getCookies();
        if(cookies.length != 0) {
            for (Cookie cookie : cookies) {
                log.info("coolie.getName" + cookie.getName());
                log.info("coolie.getValue" + cookie.getValue());
            }
        }
//        outPortByPlace.setViews(outPortByPlace.getViews() + 1);
//        placePersistenceOutPort.save(outPortByPlace);
        Place place = placePersistenceOutPort.getByPlaceId(id);
        return place;
    }

    /**
     * 카카오 API 에서 받아오는 데이터가 없을경우 저장
     * */
    private JSONObject savePlace(String keyword) {
        String changKeyword = keyword.replaceAll("\\s", "");
        JSONObject kakaoPlace = placePersistenceOutPort.getKakaoPlace(changKeyword);
        List<String> list = (List<String>) kakaoPlace.get("documents");
        JSONArray jsonArray = new JSONArray();
        JSONArray resultJsonArr = new JSONArray();
        JSONObject result = new JSONObject();
        //리팩토링
        for(int i = 0; i < list.size(); i++) {
            jsonArray.add(list.get(i));
        }
        for(int i = 0; i < list.size(); i++){
            Map<String ,Object> map = (Map<String, Object>) jsonArray.get(i);
            Optional<Long> idIndex = placePersistenceOutPort.countByPlaceId((String) map.get("id"));
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
                int view = placePersistenceOutPort.getByViews((String) map.get("id"));
                int review = placePersistenceOutPort.getByReviews((String) map.get("id"));
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
